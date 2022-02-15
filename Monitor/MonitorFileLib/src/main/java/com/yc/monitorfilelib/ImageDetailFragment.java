
package com.yc.monitorfilelib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 图片详情页面
 * @author  杨充
 * @since   2021/8/11
 */
public class ImageDetailFragment extends Fragment {
    
    private LinearLayout mLlBackLayout;
    private TextView mTvTitle;
    private TextView mTvShare;
    private ImageView mIvImageView;
    private File mFile;
    private static final String TAG = "ImageDetailFragment";
    private static final int CODE = 1000;
    private Activity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_file_image,
                container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewById(view);
        initTitleView();
        initData();
        initReadImage(mFile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //释放
        mIvImageView.setImageBitmap(null);
    }

    private void initViewById(View view) {
        mLlBackLayout = view.findViewById(R.id.ll_back_layout);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvShare = view.findViewById(R.id.tv_share);
        mIvImageView = view.findViewById(R.id.iv_image_view);
    }

    private void initTitleView() {
        mLlBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFile();
            }
        });
    }

    /**
     * Android平台中，能操作文件夹的只有两个地方：
     * sdcard
     * data/data/<package-name>/files
     */
    private void shareFile() {
        //分享
        if (mFile!=null){
            //先把文件转移到外部存储文件
            //请求权限
            //检查版本是否大于M
            if (ContextCompat.checkSelfPermission(mActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE);
            } else {
                //先把文件转移到外部存储文件
                File srcFile = new File(mFile.getPath());
                String newFilePath = FileExplorerUtils.getFileSharePath() + "/imageShare.png";
                File destFile = new File(newFilePath);
                //拷贝文件，将data/data源文件拷贝到新的目标文件路径下
                boolean copy = FileExplorerUtils.copyFile(srcFile, destFile);
                if (copy) {
                    //分享
                    FileExplorerUtils.shareFile(mActivity, destFile);
                } else {
                    Toast.makeText(getContext(), "文件保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getContext(), "file当前为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        Bundle data = getArguments();
        if (data != null) {
            mFile = (File)data.getSerializable("file_key");
        }
        if (mFile != null) {
            mTvTitle.setText(mFile.getName());
        }
    }

    private void initReadImage(File mFile) {
        if (mFile == null) {
            return;
        }
        ImageReadTask task = new ImageReadTask(this);
        task.execute(mFile);
    }

    public void finish() {
        FileExplorerActivity activity = (FileExplorerActivity)getActivity();
        if (activity != null) {
            activity.doBack(this);
        }
    }

    private static class ImageReadTask extends AsyncTask<File, Void, Bitmap> {
        private final WeakReference<ImageDetailFragment> mReference;

        public ImageReadTask(ImageDetailFragment fragment) {
            mReference = new WeakReference<>(fragment);
        }

        @Override
        protected Bitmap doInBackground(File... files) {
            return decodeSampledBitmapFromFilePath(files[0].getPath(),
                    1080, 1920);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mReference.get() != null && bitmap!=null) {
                mReference.get().mIvImageView.setImageBitmap(bitmap);
            }
        }
    }

    private static Bitmap decodeSampledBitmapFromFilePath(String imagePath, int reqWidth, int reqHeight) {
        if (imagePath == null || imagePath.length()==0){
            return null;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath,options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
