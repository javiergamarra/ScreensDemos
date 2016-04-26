# How to start a Liferay Screens Application in 20 minutes

<img src="images/WorkshopExample.png" width="200px" />

## Settings

* Add a screens dependency

	```groovy
	dependencies {
	
	    compile 'com.liferay.mobile:liferay-screens:1.4.0.rc1'
	}
	
	repositories {
	    maven {
	        url  "http://dl.bintray.com/nhpatt/liferay-mobile"
	    }
	}
	
	```

* Create a server_context.xml like the following:

	```xml
		<string name="liferay_server">http://192.168.40.253:8080</string>
		<string name="liferay_company_id">20202</string>
		<string name="liferay_group_id">20232</string>
		<integer name="liferay_portal_version">70</integer>
	```
	
* Add LoginScreenlet

* Talk about attributes

* Add material theme with layout or global theme

	```groovy
		compile 'com.liferay.mobile:liferay-material-viewset:1.4.0.rc1'
	```

	```xml
	    <color name="colorPrimary">#009688</color>
	    <color name="colorPrimaryDark">#00796B</color>
	    <color name="colorPrimaryLight">#B2DFDB</color>
	    <color name="colorAccent">#FFC107</color>
	```

* Add a DDL form

	```xml
	<string name="record_set_id">30907</string>
	<string name="structure_id">30903</string>
	```

* Customize layout

	```groovy
	compile 'com.android.support:design:23.+'
	```
	
	```xml
	<android.support.design.widget.TextInputLayout
        android:id="@+id/username_text_input_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        
        hint
        
        hide other field
	
	```
* Add a listener

* Test offline

	```xml
	
	<uses-permission android:name="android.permission.WAKE_LOCK"/>
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	
    <receiver android:name="com.liferay.mobile.screens.cache.CacheReceiver">
            <intent-filter>
                <action android:name="com.liferay.mobile.screens.auth.login.success"/>
                <action android:name="com.liferay.mobile.screens.cache.resync"/>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
            </intent-filter>
	</receiver>

	<service
            android:name="com.liferay.mobile.screens.cache.CacheSyncService"
            android:exported="false"/>

	```

* Mobile SDK

	30965

* Push test

    868241662267

	```xml
		<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
	
		<receiver
			android:name=".PushReceiver"
			android:permission="com.google.android.c2dm.permission.SEND">
			<intent-filter>
				<action android:name="com.google.android.c2dm.intent.RECEIVE"/>

				<category android:name="com.liferay.mobile.push"/>
			</intent-filter>
		</receiver>

		<service android:name=".PushService"/>
	
	```


* Show westeros app and test app (themes)

* Roadmap
	* Structured content in webviews
	* Better asset lists
	* Session cookie
	* SSO

* Documentation
	* [Tutorials](compile 'com.liferay.mobile:liferay-material-viewset:1.4.0.rc1')
	* [Reference](https://dev.liferay.com/develop/reference/-/knowledge_base/6-2/screenlets-in-liferay-screens-for-android)
	* [Videos](https://www.youtube.com/watch?v=ABxCUUg7zhs&list=PL60RdBc4OGLTcPXSHzBDEG6OFQQoBSfXL)
	* [Forums](https://www.liferay.com/community/forums/-/message_boards/category/42706063)
	* [Example projects](https://github.com/nhpatt/ScreensDemos)
	
	
	
	
	
	
	
