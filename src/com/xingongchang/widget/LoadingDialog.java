package com.xingongchang.widget;

import com.xingongchang.loadingdialog.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 加载提示对话框
 * 
 * @author LynnChurch
 * @version 创建时间：2015年3月1日 下午1:51:51
 * 
 */
public class LoadingDialog
{
	private Dialog mDialog;
	private static LoadingDialog mLoadingDialog;
	private TextView mTipTextView;
	private ImageView mSpaceshipImage;
	private RotateAnimation mAnimation;
	private static Context mContext;

	public static LoadingDialog getInstance(Context context)
	{
		if (null == mLoadingDialog || mContext != context)
		{

			mLoadingDialog = new LoadingDialog(context);
			mContext = context;

		}
		return mLoadingDialog;
	}

	private LoadingDialog(Context context)
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		mSpaceshipImage = (ImageView) v.findViewById(R.id.img);
		mTipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		mAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mAnimation.setRepeatCount(-1);// 设置重复次数
		mAnimation.setDuration(1300);
		mAnimation.setInterpolator(new LinearInterpolator());
		mSpaceshipImage.startAnimation(mAnimation);
		mTipTextView.setTextColor(Color.WHITE);

		mDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		mDialog.setCancelable(false);// 不可以用“返回键”取消
		mDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
	}

	public void setCancelable(boolean isCancelable)
	{
		mDialog.setCancelable(isCancelable);
	}

	public void setOnCancelListener(OnCancelListener listener)
	{
		mDialog.setOnCancelListener(listener);
	}

	public void show(String msg)
	{
		mSpaceshipImage.startAnimation(mAnimation);
		mTipTextView.setText(msg);
		mDialog.show();
	}

	public void dismiss()
	{
		try
		{
			if (null != mDialog && mDialog.isShowing())
			{
				mDialog.dismiss();
			}
		} catch (Exception e)
		{
			Log.e("Exception", e.getMessage());
		}
	}

	public void setCanceledOutside(boolean BeCanceled)
	{
		mDialog.setCanceledOnTouchOutside(BeCanceled);
	}

	public void setOnKeyListener(OnKeyListener onKeyListener)
	{
		mDialog.setOnKeyListener(onKeyListener);
	}

	/**
	 * 
	 * @param gravity
	 *            相当于屏幕位置
	 * @param xOffset
	 *            相对于原位置的x轴偏移
	 * @param yOffset
	 *            相对于原位置的y轴偏移
	 */
	public void setGravity(int gravity, int xOffset, int yOffset)
	{
		Window dialogWindow = mDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(gravity);
		lp.x = xOffset;
		lp.y = yOffset;
		dialogWindow.setAttributes(lp);
	}

	public void setMessage(String str)
	{
		mTipTextView.setText(str);
	}
}
