public class InfoCheckUtils {
	public static boolean checkEmail(String str) {
		String patstr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(patstr);
		Matcher m = p.matcher(str);

		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String str) {
		if (TextUtils.isEmpty(str)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean compareValue(String str1, String str2) {
		if (str1.equals(str2)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkAge(int age) {
		if (age < 13) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkAgeRange(int age) {
		if (age >= 13 && age <= 17) {
			return false;
		} else {
			return true;
		}
	}

	public static int calculateAge(String birthday) {
		int age = 0;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (!TextUtils.isEmpty(birthday)) {
				Date date = format1.parse(birthday);
				Calendar cal = Calendar.getInstance();
				int yearNow = cal.get(Calendar.YEAR);
				cal.setTime(date);
				int yearBirth = cal.get(Calendar.YEAR);
				age = yearNow - yearBirth;
			}
		} catch (ParseException e) {
			Log.e("InfoCheckUtils ParseException", e.getMessage());
			format1 = new SimpleDateFormat("MM/dd/yyyy");
			if (!TextUtils.isEmpty(birthday)) {
				Date date;
				try {
					date = format1.parse(birthday);
					Calendar cal = Calendar.getInstance();
					int yearNow = cal.get(Calendar.YEAR);
					cal.setTime(date);
					int yearBirth = cal.get(Calendar.YEAR);
					age = yearNow - yearBirth;
				} catch (ParseException e1) {
					Log.e("InfoCheckUtils ParseException", e1.getMessage());
				}
			}
		}
		return age;
	}
}
