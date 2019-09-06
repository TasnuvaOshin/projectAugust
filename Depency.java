dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    //noinspection GradleCompatible
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'androidx.appcompat:appcompat:1.0.0-alpha1'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.2'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0-alpha1'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

    //noinspection GradleCompatible
    implementation 'com.android.support:design:28.0.0'
    implementation 'com.android.support:support-v13:29.0.0'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.squareup.okhttp3:okhttp:4.1.0'
    implementation 'com.nbsp:library:1.8'
    implementation 'com.android.volley:volley:1.1.1'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'com.karumi:dexter:5.0.0'
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'com.google.android.gms:play-services-places:17.0.0'
    implementation 'com.google.android.libraries.places:places:1.0.0'
    implementation 'de.hdodenhof:circleimageview:3.0.0'
    //noinspection GradleDependency
    implementation 'com.github.bumptech.glide:glide:4.8.0'
    //noinspection GradleDependency
    implementation 'com.google.firebase:firebase-analytics:17.0.1'

    annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'

    implementation 'com.google.firebase:firebase-core:17.1.0'
    implementation 'com.google.firebase:firebase-auth:19.0.0'
    implementation 'com.google.firebase:firebase-messaging:20.0.0'
    implementation 'com.google.firebase:firebase-storage:19.0.0'
    implementation 'com.google.firebase:firebase-database:19.0.0'
    implementation 'com.firebaseui:firebase-ui-database:4.3.2'
    implementation 'com.firebaseui:firebase-ui-firestore:4.3.2'
    implementation 'com.firebaseui:firebase-ui-auth:4.3.2'


    implementation 'com.madgag:scpkix-jdk15on:1.47.0.1'
    implementation 'com.itextpdf:itextpdf:5.0.6'

    //sslcomz


    implementation 'com.google.code.gson:gson:2.8.5'
    implementation(name: 'sslCommerzSdk', ext: 'aar')
}
