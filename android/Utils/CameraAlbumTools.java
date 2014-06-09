package com.aaisme.Aa.component;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.aaisme.Aa.R;
import com.aaisme.Aa.util.LogUtil;

public class CameraAlbumTools {
	private Activity mActivity;
	private Fragment mFragment;
	private boolean mActivityFlag;
	public static final int ALBUM_REQUEST_CODE = 11;
	public static final int CAMERA_REQUEST_CODE = 12;
	private String mTempFilePath;
	private String mTempFileName;
	private Uri outputFileUri;

//	public CameraAlbumTools(Activity activity) {
//		this.mActivity = activity;
//	}
//
//	public CameraAlbumTools(Fragment fragment) {
//		mActivity = fragment.getActivity();
//	}

//	public Dialog createDialog(final Activity context) {
//		AlertDialog dialog = new AlertDialog.Builder(context)
//				.setTitle(context.getString(R.string.choose))
//				.setItems(
//						new String[] { context.getString(R.string.camera),
//								context.getString(R.string.album) },
//						new OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								if (which == 0) {
//									openCamera();
//								} else if (which == 1) {
//									Intent albumIntent = new Intent(
//											Intent.ACTION_GET_CONTENT);
//									albumIntent.setType("image/*");
//									context.startActivityForResult(albumIntent,
//											ALBUM_REQUEST_CODE);
//								}
//							}
//						}).show();
//
//		return dialog;
//	}
	
	public Builder getDialog(Fragment context){
		Builder builder = new AlertDialog.Builder(context.getActivity());
		return builder;
	}

	public void createDialog(final Object  object) {
		if (object instanceof Fragment) {
			mFragment = (Fragment) object;
			mActivity = mFragment.getActivity();
			mActivityFlag = false;
		}else if(object instanceof Activity) {
			mActivity = (Activity) object;
			mActivityFlag = true;
		}else{
			return;
		}
		new AlertDialog.Builder(mActivity)
		.setTitle(mActivity.getString(R.string.choose))
		.setItems(
						new String[] { mActivity.getString(R.string.camera),
								mActivity.getString(R.string.album) },
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == 0) {
									openCamera();
								} else if (which == 1) {
									Intent albumIntent = new Intent(
											Intent.ACTION_GET_CONTENT);
									albumIntent.setType("image/*");
									if (mActivityFlag) {
										mActivity.startActivityForResult(albumIntent,
												ALBUM_REQUEST_CODE);
									}else{
										mFragment.startActivityForResult(albumIntent,
												ALBUM_REQUEST_CODE);
									}
								}
							}
						}).show();
		
	}
	private void openCamera() {
		mTempFileName = System.currentTimeMillis() + ".jpg";
		mTempFilePath = Constants.TEMP_DIR  + mTempFileName;
		File mTempFile = new File(mTempFilePath);
		outputFileUri = Uri.fromFile(mTempFile);
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		if (mActivityFlag) {
			mActivity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
		}else{
			mFragment.startActivityForResult(intent, CAMERA_REQUEST_CODE);
		}
	}

	public void onResult(int requestCode, int resultCode, Intent data) {
		LogUtil.i(getClass().getSimpleName() + "--onResult");
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case ALBUM_REQUEST_CODE:
				selectAttachFile(data.getData(), ALBUM_REQUEST_CODE);
				break;
			case CAMERA_REQUEST_CODE:
				addImageFromCamera(data);
				break;
			default:
				break;
			}
		}
	}

	private void selectAttachFile(Uri uri, int what) {
		if (uri != null) {
			ContentResolver cr = mActivity.getContentResolver();
			String[] columns = new String[1];
			if (what == ALBUM_REQUEST_CODE) {
				columns[0] = MediaStore.Images.Media.DATA;
			}
			Cursor c = cr.query(uri, columns, null, null, null);
			if (c != null) {
				c.moveToFirst();
				if ("image".equalsIgnoreCase(Utils.getMIMEType(c.getString(0)))) {
					mTempFilePath = c.getString(0);
				}
			} else {
				if ("image".equalsIgnoreCase(Utils.getMIMEType(uri.getPath()))) {
					mTempFilePath = uri.getPath();
				}
			}
		} else {
		}
	}

	private void addImageFromCamera(Intent data) {
		Uri uri = null;
		if (data != null) {
			uri = data.getData();
		}
		if (null != uri) {
			ContentResolver cr = mActivity.getContentResolver();
			Cursor c = cr.query(uri, null, null, null, null);
			String filePath;
			if (c != null) {
				c.moveToFirst();
				filePath = c.getString(c
						.getColumnIndex(MediaStore.MediaColumns.DATA));
			} else {
				filePath = uri.getPath();
			}
			if (filePath.compareTo(mTempFilePath) != 0) {
				Utils.copyFile(filePath, mTempFilePath);
				Utils.copyFile(filePath, Constants.DICM_DIR 
						+ mTempFileName);
				Utils.delFile(filePath);
			} else {
				Utils.copyFile(filePath, Constants.DICM_DIR 
						+ mTempFileName);
			}
		} else {
			Utils.copyFile(mTempFilePath, Constants.DICM_DIR 
					+ mTempFileName);
		}
	}

	public String getFilePath() {
		return mTempFilePath;
	}
	/////////////////////////////////////
	
	private static final int TAKE_PICTURE = 51;
	private static final int CROP_PICTURE = 52;
	private static final int CHOOSE_PICTURE = 53;
	private Uri imageUri;
	private String imagePath;
	private String imageName;
	private Bitmap mBitmap;
	
	/**
	 * 图片存放的目录
	 * 默认：Constants.DICM_DIR 
	 * 如果是IM：
	 */
	private String mDirectory;
	public void cameraAlbumDialog(final Object  object) {
		if (object instanceof Fragment) {
			mFragment = (Fragment) object;
			mActivity = mFragment.getActivity();
			mActivityFlag = false;
		}else if(object instanceof Activity) {
			mActivity = (Activity) object;
			mActivityFlag = true;
		}else{
			return;
		}
//		imagePath = Constants.DICM_DIR  + System.currentTimeMillis() + Constants.IMAGE_SUFFIX;
		imageName =  System.currentTimeMillis() + "";
		imagePath = getDirectory()  +imageName+ Constants.IMAGE_SUFFIX;
		File temp = new File(imagePath);
		imageUri = Uri.fromFile(temp);
		new AlertDialog.Builder(mActivity)
		.setTitle(mActivity.getString(R.string.choose))
		.setItems(
						new String[] { mActivity.getString(R.string.camera),
								mActivity.getString(R.string.album) },
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == 0) {
									cameraCapture();
								} else if (which == 1) {
									chooseAlbum(Constants.CAMERA_ALBUM_IMAGE_OUTX, Constants.CAMERA_ALBUM_IMAGE_OUTY);
								}
							}
						}).show();
		
	}
	
	public void cameraCapture(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		if (mActivityFlag) {
			mActivity.startActivityForResult(intent, TAKE_PICTURE);
		}else{
			mFragment.startActivityForResult(intent, TAKE_PICTURE);
		}
	}
	
	private void chooseAlbum(int outputX, int outputY){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false); // no face detection
		if (mActivityFlag) {
			mActivity.startActivityForResult(intent, CHOOSE_PICTURE);
		}else{
			mFragment.startActivityForResult(intent, CHOOSE_PICTURE);
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != Activity.RESULT_OK){
			return;
		}else{
			switch (requestCode) {
			case TAKE_PICTURE:
				cropImageUri(imageUri, Constants.CAMERA_ALBUM_IMAGE_OUTX, Constants.CAMERA_ALBUM_IMAGE_OUTY, CROP_PICTURE);
				break;
			case CROP_PICTURE:
				mBitmap = decodeUriAsBitmap(imageUri);
				
				int degree = Utils.readPictureDegree(imagePath);
				if (degree > 0) {
					mBitmap = Utils.rotateBitmap(getBitmap(), degree);
				}
				break;
			case CHOOSE_PICTURE:
				mBitmap = decodeUriAsBitmap(imageUri);
				break;
			default:
				break;
			}
		}
	}
	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		if (mActivityFlag) {
			mActivity.startActivityForResult(intent, requestCode);
		}else{
			mFragment.startActivityForResult(intent, requestCode);
		}
	}
	
	private Bitmap decodeUriAsBitmap(Uri uri){
		File tempfile = new File(imagePath);
		LogUtil.i("decodeUriAsBitmap----" + tempfile.length());
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(mActivity.getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	private Bitmap decodeFromFile(Uri uri){
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(mActivity.getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}
	
	/**
	 * 在onDestory方法中调用此方法
	 * @author xuechinahb@gmail.com
	 * May 10, 2014
	 */
	public void recyleBitmap(){
		if (null != mBitmap && !mBitmap.isRecycled()) {
			mBitmap.recycle();
		}
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setDirectory(String mDirectory) {
		this.mDirectory = mDirectory;
	}

	public String getDirectory() {
		if (null == mDirectory) {
			return Constants.DICM_DIR;
		}
		return mDirectory;
	}

	public String getImageName() {
		return imageName;
	}
	
	
}
