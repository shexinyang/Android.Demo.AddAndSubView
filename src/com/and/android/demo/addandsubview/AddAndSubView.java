package com.and.android.demo.addandsubview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * 
 * @author ZJJ
 *
 */
public class AddAndSubView extends LinearLayout
{
	Context context;
	LinearLayout mainLinearLayout;   //主View，即AddAndSubView
	LinearLayout leftLinearLayout;   //内部左view
	LinearLayout centerLinearLayout;   //中间view
	LinearLayout rightLinearLayout;  //内部右view
	OnNumChangeListener onNumChangeListener;
	Button addButton;
	Button subButton;
	EditText editText;
	int num;          //editText中的数值
	int editTextLayoutWidth;  //editText视图的宽度
	int editTextLayoutHeight;  //editText视图的宽度
	int editTextMinimumWidth;  //editText视图的最小宽度
	int editTextMinimumHeight;  //editText视图的最小高度
	int editTextMinHeight;  //editText文本区域的最小高度
	int editTextHeight;  //editText文本区域的高度
	

	public AddAndSubView(Context context)
	{
		super(context);
		this.context = context;
		num = 0;
		control();
	}

	/**
	 * 带初始数据实例化
	 * @param context
	 * @param 初始数据
	 */
	public AddAndSubView(Context context, int num)
	{
		super(context);
		this.context = context;
		this.num = num;
		control();
	}
	
	
	
	
	public AddAndSubView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		num = 0;
		control();
	}

	/**
	 * 
	 */
	private void control()
	{
		initTextWithHeight();
		initialise();          //实例化内部view
		setViewsLayoutParm();  //设置内部view的布局参数
		insertView();            //将子view放入linearlayout中
		setViewListener();
	}
	
	
	/**
	 * 初始化EditText宽高参数
	 */
	private void initTextWithHeight()
	{
		editTextLayoutWidth = -1;
		editTextLayoutHeight = -1;
		editTextMinimumWidth = -1;
		editTextMinimumHeight = -1;
		editTextMinHeight = -1;
		editTextHeight = -1;
	}
	
	
	/**
	 * 实例化内部View
	 */
	private void initialise()
	{
		mainLinearLayout = new LinearLayout(context);
		leftLinearLayout = new LinearLayout(context);
		centerLinearLayout = new LinearLayout(context);
		rightLinearLayout = new LinearLayout(context);
		addButton = new Button(context);
		subButton = new Button(context);
		editText = new EditText(context);
		
		addButton.setText("+");
		subButton.setText("-");
		addButton.setTag("+");
		subButton.setTag("-");
		//设置输入类型为数字
		editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		editText.setText(String.valueOf(num));
	}
	
	/**
	 * 设置内部view的布局参数
	 */
	private void setViewsLayoutParm()
	{
		LayoutParams viewLayoutParams = new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		
		addButton.setLayoutParams(viewLayoutParams);
		subButton.setLayoutParams(viewLayoutParams);
		editText.setLayoutParams(viewLayoutParams);
		editText.setGravity(Gravity.CENTER);
		setTextWidthHeight();
		
		viewLayoutParams.gravity = Gravity.CENTER;
		centerLinearLayout.setLayoutParams(viewLayoutParams);
		//让editText不自动获得焦点
		centerLinearLayout.setFocusable(true);
		centerLinearLayout.setFocusableInTouchMode(true);
		
		viewLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
		viewLayoutParams.weight = 1.0f;
		leftLinearLayout.setLayoutParams(viewLayoutParams);  //参数：宽、高、比重，比重为1.0
		rightLinearLayout.setLayoutParams(viewLayoutParams);  //参数：宽、高、比重，比重为1.0
		
		viewLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
		mainLinearLayout.setLayoutParams(viewLayoutParams);
		mainLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
	}
	
	
	/**
	 * 设置EditText视图和文本区域宽高
	 */
	private void setTextWidthHeight()
	{
		float fPx;
		
		//设置视图最小宽度
		if (editTextMinimumWidth < 0)
		{
			// 将数据从dip(即dp)转换到px，第一参数为数据原单位（此为DIP），第二参数为要转换的数据值
			fPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					80f, context.getResources().getDisplayMetrics());
			editTextMinimumWidth = Math.round(fPx);
		}
		editText.setMinimumWidth(editTextMinimumWidth);
		
		//设置文本区域高度
		if (editTextHeight > 0)
		{
			if (editTextMinHeight >= 0 && editTextMinHeight > editTextHeight)
			{
				editTextHeight = editTextMinHeight;
			}
			editText.setHeight(editTextHeight);
		}
		
		//设置视图高度
		if (editTextLayoutHeight > 0)
		{
			if (editTextMinimumHeight > 0  && 
					editTextMinimumHeight > editTextLayoutHeight)
			{
				editTextLayoutHeight = editTextMinimumHeight;
			}
			
			LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
			layoutParams.height = editTextLayoutHeight;
			editText.setLayoutParams(layoutParams);
		}
		
		//设置视图宽度
		if (editTextLayoutWidth > 0)
		{
			if (editTextMinimumWidth > 0  && 
					editTextMinimumWidth > editTextLayoutWidth)
			{
				editTextLayoutWidth = editTextMinimumWidth;
			}
			
			LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
			layoutParams.width = editTextLayoutWidth;
			editText.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 将子view放入linearlayout中
	 */
	private void insertView()
	{
		mainLinearLayout.addView(leftLinearLayout, 0);
		mainLinearLayout.addView(centerLinearLayout, 1);
		mainLinearLayout.addView(rightLinearLayout, 2);
		
		leftLinearLayout.addView(addButton);
		centerLinearLayout.addView(editText);
		rightLinearLayout.addView(subButton);

		addView(mainLinearLayout);  //将整块视图添加进当前AddAndSubView中
	}

	/**
	 * 设置editText中的值
	 * @param num
	 */
	public void setNum(int num)
	{
		this.num = num;
		editText.setText(String.valueOf(num));
	}
	
	/**
	 * 获取editText中的值
	 * @return
	 */
	public int getNum()
	{
		if ( editText.getText().toString() != null )
		{
			return Integer.parseInt(editText.getText().toString());
		}
		else {
			return 0;
		}
	}

	
	/**
	 * 设置EditText视图的最小高度
	 * @param minimumWidth EditText的最小高度，单位px
	 */
	public void setEditTextMinimumWidth(int editTextMinimumWidth)
	{
		//设置视图最小宽度
		if (editTextMinimumWidth > 0)
		{
			this.editTextMinimumWidth = editTextMinimumWidth;
			editText.setMinimumWidth(editTextMinimumWidth);
		}
		
	}

	/**
	 * 设置EditText视图的最小高度
	 * @param editTextMinimumHeight EditText视图的最小高度,单位：px
	 */
	public void setEditTextMinimumHeight(int editTextMinimumHeight)
	{
		//设置视图最小高度
		if (editTextMinimumHeight > 0)
		{
			this.editTextMinimumHeight = editTextMinimumHeight;
			editText.setMinimumHeight(editTextMinimumHeight);
		}
	}

	/**
	 * 设置EditText文本区域的最小高度
	 * @param editTextMinHeight EditText文本区域的最小高度,单位：px
	 */
	public void setEditTextMinHeight(int editTextMinHeight)
	{
		//设置文本区域最小高度
		if (editTextMinHeight > 0)
		{
			this.editTextMinHeight = editTextMinHeight;
			editText.setMinHeight(editTextMinHeight);
		}
	}

	/**
	 * 设置EditText文本区域的高度
	 * @param editTextHeight EditText文本区域的高度,单位：px
	 */
	public void setEditTextHeight(int editTextHeight)
	{
		this.editTextHeight = editTextHeight;
		setTextWidthHeight();
	}

	/**
	 * 设置EditText视图的宽度
	 * @param editTextLayoutWidth 设置EditText视图的宽度,单位px
	 */
	public void setEditTextLayoutWidth(int editTextLayoutWidth)
	{
		this.editTextLayoutWidth = editTextLayoutWidth;
		setTextWidthHeight();
	}

	/**
	 * 设置EditText视图的高度
	 * @param editTextLayoutHeight EditText视图的最小高度，单位px
	 */
	public void setEditTextLayoutHeight(int editTextLayoutHeight)
	{
		this.editTextLayoutHeight = editTextLayoutHeight;
		setTextWidthHeight();
	}

	/**
	 * 以Drawable形式 设置按钮背景图
	 * @param addBtnDrawable 加号背景图
	 * @param subBtnDrawable 减号背景图
	 */
	public void Drawable(Drawable addBtnDrawable, Drawable subBtnDrawable)
	{
		//不推荐用setBackgroundDrawable，新API推荐用setBackground（在API 16中）
		addButton.setBackgroundDrawable(addBtnDrawable);
		subButton.setBackgroundDrawable(subBtnDrawable);
		addButton.setText("");
		subButton.setText("");
	}
	
	/**
	 * 以资源Resource形式 设置按钮背景图
	 * @param addBtnResource 加号背景图
	 * @param subBtnResource 减号背景图
	 */
	public void setButtonBgResource(int addBtnResource, int subBtnResource)
	{
		addButton.setBackgroundResource(addBtnResource);
		subButton.setBackgroundResource(subBtnResource);
		addButton.setText("");
		subButton.setText("");
	}
	
	/**
	 * 设置按钮背景色
	 * @param addBtnColor 加号背景色
	 * @param subBtnColor 减号背景色
	 */
	public void setButtonBgColor(int addBtnColor, int subBtnColor)
	{
		addButton.setBackgroundColor(addBtnColor);
		subButton.setBackgroundColor(subBtnColor);
	}
	
	/**
	 * 设置EditText文本变化监听
	 * @param onNumChangeListener
	 */
	public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener)
	{
		this.onNumChangeListener = onNumChangeListener;
	}
	
	
	/**
	 * 设置文本变化相关监听事件
	 */
	private void setViewListener()
	{
		addButton.setOnClickListener(new OnButtonClickListener());
		subButton.setOnClickListener(new OnButtonClickListener());
		editText.addTextChangedListener(new OnTextChangeListener());
	}
	
	
	/**
	 * 加减按钮事件监听器
	 * @author ZJJ
	 *
	 */
	class OnButtonClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			String numString = editText.getText().toString();
			if (numString == null || numString.equals(""))
			{
				num = 0;
				editText.setText("0");
			} else
			{
				if (v.getTag().equals("+"))
				{
					if (++num < 0)  //先加，再判断
					{
						num--;
						Toast.makeText(context, "请输入一个大于0的数字",
								Toast.LENGTH_SHORT).show();
					} else
					{
						editText.setText(String.valueOf(num));
						
						if (onNumChangeListener != null)
						{
							onNumChangeListener.onNumChange(AddAndSubView.this, num);
						}
					}
				} else if (v.getTag().equals("-"))
				{
					if (--num < 0)  //先减，再判断
					{
						num++;
						Toast.makeText(context, "请输入一个大于0的数字",
								Toast.LENGTH_SHORT).show();
					} else
					{
						editText.setText(String.valueOf(num));
						if (onNumChangeListener != null)
						{
							onNumChangeListener.onNumChange(AddAndSubView.this, num);
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * EditText输入变化事件监听器
	 * @author ZJJ
	 *
	 */
	class OnTextChangeListener implements TextWatcher
	{

		@Override
		public void afterTextChanged(Editable s)
		{
			String numString = s.toString();
			if(numString == null || numString.equals(""))
			{
				num = 0;
				if (onNumChangeListener != null)
				{
					onNumChangeListener.onNumChange(AddAndSubView.this, num);
				}
			}
			else {
				int numInt = Integer.parseInt(numString);
				if (numInt < 0)
				{
					Toast.makeText(context, "请输入一个大于0的数字",
							Toast.LENGTH_SHORT).show();
				} else
				{
					//设置EditText光标位置 为文本末端
					editText.setSelection(editText.getText().toString().length());
					num = numInt;
					if (onNumChangeListener != null)
					{
						onNumChangeListener.onNumChange(AddAndSubView.this, num);
					}
				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count)
		{
			
		}
		
	}
	
	
	public interface OnNumChangeListener
	{
		/**
		 * 输入框中的数值改变事件
		 * @param view 整个AddAndSubView
		 * @param num 输入框的数值
		 */
		public void onNumChange(View view, int num);
	}
	
}
