# Release এবং Play Store Upload গাইড

## প্রস্তুতি

### ১. জেকেএস কী জেনারেট করুন (প্রথম বার শুধুমাত্র)

```bash
keytool -genkey -v -keystore release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias release
```

প্রশ্নের উত্তর দিন:
- **Keystore Password**: শক্তিশালী পাসওয়ার্ড তৈরি করুন
- **Key password**: একই পাসওয়ার্ড ব্যবহার করুন
- **First and Last Name**: আপনার নাম
- **Organizational Unit**: Optional
- **Organization**: Optional
- **City**: আপনার শহর
- **State**: আপনার প্রদেশ
- **Country Code**: BD

### ২. build.gradle.kts আপডেট করুন

`app/build.gradle.kts` ফাইলে যোগ করুন:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file("../release-key.jks")
        storePassword = "আপনার_জেকেএস_পাসওয়ার্ড"
        keyAlias = "release"
        keyPassword = "আপনার_কী_পাসওয়ার্ড"
    }
}

android {
    // ... অন্যান্য কনফিগ
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

## বিল্ড করুন

### AAB (আনুষ্ঠানিক সুপারিশকৃত)

```bash
./gradlew bundleRelease
```

**আউটপুট**: `app/build/outputs/bundle/release/app-release.aab`

### বা APK (সরাসরি ইনস্টলেশন)

```bash
./gradlew assembleRelease
```

**আউটপুট**: `app/build/outputs/apk/release/app-release.apk`

## Play Store এ আপলোড করুন

1. Google Play Console খুলুন: https://play.google.com/console
2. আপনার অ্যাপে যান
3. **রিলিজ → প্রোডাকশন**
4. **নতুন রিলিজ তৈরি করুন**
5. AAB ফাইল আপলোড করুন
6. রিলিজ নোট যোগ করুন
7. **পর্যালোচনার জন্য জমা দিন**

## ট্রাবলশুটিং

### Build ফেইল হলে:

```bash
# ক্যাশ ক্লিয়ার করুন
./gradlew clean

# পুনরায় বিল্ড করুন
./gradlew bundleRelease
```

### Signing ত্রুটি:

- পাসওয়ার্ড সঠিক কিনা চেক করুন
- JDK সঠিকভাবে ইনস্টল আছে কিনা চেক করুন
- `release-key.jks` ফাইল প্রজেক্ট রুটে আছে কিনা চেক করুন

## নিরাপত্তা টিপস

⚠️ **গুরুত্বপূর্ণ**:
- `release-key.jks` ফাইল সুরক্ষিত রাখুন
- পাসওয়ার্ড কখনো শেয়ার করবেন না
- Git এ কী ফাইল কমিট করবেন না
- ব্যাকআপ নিয়ে রাখুন (একবার হারালে পুনরুদ্ধার করতে পারবেন না)

## আপডেট রিলিজ করা

পরবর্তী আপডেটের জন্য:

1. `versionCode` বৃদ্ধি করুন: `app/build.gradle.kts`
   ```kotlin
   versionCode = 2  // পূর্ববর্তী: 1
   versionName = "1.0.1"
   ```

2. পুনরায় বিল্ড করুন:
   ```bash
   ./gradlew bundleRelease
   ```

3. Play Console এ আপলোড করুন

## সাহায্য

সমস্যার জন্য:
📧 Email: mdrasel481726@gmail.com
🔗 GitHub: https://github.com/mdrasel481726-ctrl/rasels-hisab-pro