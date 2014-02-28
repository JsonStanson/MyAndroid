public class AbstractBaseActivity extends SherlockFragmentActivity {

	private long exitTime = 0;
	public int mStackLevel = 1;
	public boolean isHomeActivity = false;
	protected ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(SampleList.THEME);
		actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
				.getColor(R.color.actionbar_background)));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (isHomeActivity) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(),
							"Press again to quit the app", Toast.LENGTH_SHORT)
							.show();
					exitTime = System.currentTimeMillis();
				} else {
					finish();
					ApplicationProject.getInstance().exit();
				}
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			if (!isHomeActivity) {
				finish();
			} else {

			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			if (!isHomeActivity) {
				finish();
			} else {

			}
		} else if (getString(R.string.action_item_synchronize).equals(
				item.getTitle())) {
			launchTask();
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		SubMenu subMenu1 = menu.addSubMenu(UserInfos.get(this).getUserInfo()
				.getUsr_firstname());
		subMenu1.add(getString(R.string.action_item_synchronize));
		subMenu1.add(getString(R.string.action_item_faq));
		subMenu1.add(getString(R.string.action_item_sign_out));

		MenuItem subMenu1Item = subMenu1.getItem();
		subMenu1Item
				.setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_dark);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		subMenu1Item.setOnMenuItemClickListener(new AbstractBaseImpl(this));
		return super.onCreateOptionsMenu(menu);
	}

	public void replaceFragment(Fragment newFragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fl_home_content, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void replaceFragment(Fragment newFragment, Bundle bundle) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		newFragment.setArguments(bundle);
		ft.replace(R.id.fl_home_content, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void redirectFragment(Fragment newFragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.fl_home_content, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void redirectFragment(Fragment newFragment, Bundle bundle) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		newFragment.setArguments(bundle);
		ft.add(R.id.fl_home_content, newFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void launchTask() {
		UploadProjectUserProspectTask mTask = new UploadProjectUserProspectTask(
				this);
		mTask.excute();
	}
}
