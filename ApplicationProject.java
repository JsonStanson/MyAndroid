public class ApplicationProject extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	private static ApplicationProject instance;
	public static boolean DEBUG = true;
	public static float SCREEN_DENSITY;
	public static String backgroundPath;

	public void onCreate() {
		super.onCreate();
		setDebug(true);

		backgroundPath = Environment.getExternalStorageDirectory().getPath()
				+ "/Android/data/" + getPackageName() + "/";

		SCREEN_DENSITY = getResources().getDisplayMetrics().densityDpi;
	}

	public static String getUDID(Context context) {
		String id = ((TelephonyManager) context
				.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
		return id;
	}

	public void setDebug(boolean debugging) {
		DEBUG = debugging;
	}

	public static double calculateDistance(double lat1, double lon1,
			double lat2, double lon2) {
		float[] results = { 0.0f };
		Location.distanceBetween(lat1, lon1, lat2, lon2, results);
		return results[0];
	}

	public static ApplicationProject getInstance() {
		if (null == instance) {
			instance = new ApplicationProject();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			if (activity != null && !activity.isFinishing()) {
				activity.finish();
			}
		}
		System.exit(0);
	}
}
