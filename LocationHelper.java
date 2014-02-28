public class LocationHelper implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener {

	private WeakReference<Activity> mWeakReference;
	public LocationClient mLocationClient;
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	private View parentView;

	public LocationHelper(Context mContext) {
		this.mWeakReference = new WeakReference<Activity>((Activity) mContext);
	}

	private void setUpLocationClientIfNeeded() {
		if (mLocationClient == null) {
			mLocationClient = new LocationClient(mWeakReference.get(), this, // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
	}

	public void getLocation() {
		if (GeneralTools.openGPSSettings(mWeakReference.get())) {
			setUpLocationClientIfNeeded();
			mLocationClient.connect();
		} else {
			GeneralTools.initPopupWindowGPSSetting(mWeakReference.get(),
					parentView);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		double mLat = location.getLatitude();
		double mLon = location.getLongitude();
		LoginInfos.get(mWeakReference.get()).getLoginInfo().mbr_latitude = String
				.valueOf(mLat);
		LoginInfos.get(mWeakReference.get()).getLoginInfo().mbr_longitude = String
				.valueOf(mLon);
		LoginInfos.get(mWeakReference.get()).saveDataToPreferences();
		mLocationClient.disconnect();
		launchAroundMeTask(parentView, String.valueOf(mLat),
				String.valueOf(mLon));
		launchUpdateMyLocationTask(String.valueOf(mLat), String.valueOf(mLon));
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		mLocationClient.requestLocationUpdates(REQUEST, this);

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	public void setParentView(View _parentView) {
		parentView = _parentView;
	}

	public void launchAroundMeTask(View parentView, String lat, String lng) {
		GetHomeInfoTask mTask = GetHomeInfoTask.get(mWeakReference.get(),
				parentView);
		mTask.params.put(Constants.Key.SSO, LoginInfos
				.get(mWeakReference.get()).getLoginInfo().sso_value);
		mTask.params.put(Constants.Key.LONGITUDE, lng);
		mTask.params.put(Constants.Key.LATITUDE, lat);
		/**
		 * filter for search people around you
		 */
		if (!FilterInfos.gender.equals(""))
			mTask.params.put(Constants.Key.FILTER_GENDER, FilterInfos.gender);
		if (!FilterInfos.minAge.equals(""))
			mTask.params.put(Constants.Key.FILTER_MINAGE, FilterInfos.minAge);
		if (!FilterInfos.maxAge.equals(""))
			mTask.params.put(Constants.Key.FILTER_MAXAGE, FilterInfos.maxAge);
		if (!FilterInfos.interests.equals(""))
			mTask.params.put(Constants.Key.FILTER_INTEREST,
					FilterInfos.interests);
		if (!FilterInfos.lookingFor.equals(""))
			mTask.params.put(Constants.Key.FILTER_LOOKINGFOR,
					FilterInfos.lookingFor);
		mTask.params.put(Constants.Key.FILTER_DISTANCE,
				String.valueOf(FilterInfos.distance));
		mTask.excute();
	}

	public void launchUpdateMyLocationTask(String lat, String lng) {
		UpdateUserLocationTask mTask = new UpdateUserLocationTask(
				mWeakReference.get());
		mTask.params.put(Constants.Key.SSO, LoginInfos
				.get(mWeakReference.get()).getLoginInfo().sso_value);
		mTask.params.put(Constants.Key.LONGITUDE, lng);
		mTask.params.put(Constants.Key.LATITUDE, lat);
		mTask.excute();
	}
}
