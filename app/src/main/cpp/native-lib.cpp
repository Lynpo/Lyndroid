#include <jni.h>
#include <string>
#include "lynpo-const.h"

const std::string Lynpo_Const::LYNPO_NAME = "Lynpo";
const char HexCode[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
/**
 * the signature sha1 is generated with
 * Android Studio - Build > Generate Signed APK > ...
 */
const char *app_signature_sha1 = "8F23CB6BB39860937CF9E2B863C95A1CE1FAA742";

jobject getApplication(JNIEnv *env) {
    jclass localClass = (env)->FindClass("android/app/ActivityThread");
    if (localClass!=NULL)
    {
        jmethodID getapplication = (env)->GetStaticMethodID(localClass, "currentApplication", "()Landroid/app/Application;");
        if (getapplication!=NULL)
        {
            jobject application = (env)->CallStaticObjectMethod(localClass, getapplication);
            return application;
        }
        return NULL;
    }
    return NULL;
}

jbyteArray getHashSign(JNIEnv *env) {
    //获取到Context
    jobject context= getApplication(env);
    jclass  activity = (env)->GetObjectClass(context);
    // 得到 getPackageManager 方法的 ID
    jmethodID methodID_func = (env)->GetMethodID(activity, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    // 获得PackageManager对象
    jobject packageManager = (env)->CallObjectMethod(context,methodID_func);
    jclass packageManagerclass = (env)->GetObjectClass(packageManager);
    //得到 getPackageName 方法的 ID
    jmethodID methodID_pack = (env)->GetMethodID(activity,"getPackageName", "()Ljava/lang/String;");
    //获取包名
    jstring name_str = (jstring)((env)->CallObjectMethod(context, methodID_pack));
    // 得到 getPackageInfo 方法的 ID
    jmethodID methodID_pm = (env)->GetMethodID(packageManagerclass,"getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    // 获得应用包的信息
    jobject package_info = (env)->CallObjectMethod(packageManager, methodID_pm, name_str, 64);
    // 获得 PackageInfo 类
    jclass package_infoclass = (env)->GetObjectClass(package_info);
    // 获得签名数组属性的 ID
    jfieldID fieldID_signatures = (env)->GetFieldID(package_infoclass,"signatures", "[Landroid/content/pm/Signature;");
    // 得到签名数组，待修改
    jobject signatur = (env)->GetObjectField(package_info, fieldID_signatures);
    jobjectArray  signatures = (jobjectArray)(signatur);
    // 得到签名
    jobject signature = (env)->GetObjectArrayElement(signatures, 0);
    // 获得 Signature 类，待修改
    jclass signature_clazz = (env)->GetObjectClass(signature);
    //---获得签名byte数组
    jmethodID tobyte_methodId = (env)->GetMethodID(signature_clazz, "toByteArray", "()[B");
    jbyteArray signature_byte = (jbyteArray) (env)->CallObjectMethod(signature, tobyte_methodId);

    //把byte数组转成流
    jclass byte_array_input_class=(env)->FindClass("java/io/ByteArrayInputStream");
    jmethodID init_methodId=(env)->GetMethodID(byte_array_input_class,"<init>","([B)V");
    jobject byte_array_input=(env)->NewObject(byte_array_input_class,init_methodId,signature_byte);
    //实例化X.509
    jclass certificate_factory_class=(env)->FindClass("java/security/cert/CertificateFactory");
    jmethodID certificate_methodId=(env)->GetStaticMethodID(certificate_factory_class,"getInstance","(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;");
    jstring x_509_jstring=(env)->NewStringUTF("X.509");
    jobject cert_factory=(env)->CallStaticObjectMethod(certificate_factory_class,certificate_methodId,x_509_jstring);
    //certFactory.generateCertificate(byteIn);
    jmethodID certificate_factory_methodId=(env)->GetMethodID(certificate_factory_class,"generateCertificate",("(Ljava/io/InputStream;)Ljava/security/cert/Certificate;"));
    jobject x509_cert=(env)->CallObjectMethod(cert_factory,certificate_factory_methodId,byte_array_input);

    jclass x509_cert_class=(env)->GetObjectClass(x509_cert);
    jmethodID x509_cert_methodId=(env)->GetMethodID(x509_cert_class,"getEncoded","()[B");
    jbyteArray cert_byte=(jbyteArray)(env)->CallObjectMethod(x509_cert,x509_cert_methodId);

    //MessageDigest.getInstance("SHA1")
    jclass message_digest_class=(env)->FindClass("java/security/MessageDigest");
    jmethodID methodId=(env)->GetStaticMethodID(message_digest_class,"getInstance","(Ljava/lang/String;)Ljava/security/MessageDigest;");
    //如果取SHA1则输入SHA1
    jstring sha1_jstring=(env)->NewStringUTF("SHA1");
    //如果取MD5则输入MD5
//    jstring sha1_jstring=(env)->NewStringUTF("MD5");
    jobject sha1_digest=(env)->CallStaticObjectMethod(message_digest_class,methodId,sha1_jstring);
    //sha1.digest (certByte)
    methodId=(env)->GetMethodID(message_digest_class,"digest","([B)[B");
    jbyteArray sha1_byte=(jbyteArray)(env)->CallObjectMethod(sha1_digest,methodId,cert_byte);

    return sha1_byte;
}

bool hashSignValid(JNIEnv *env, const  char *sign) {
    jbyteArray sha1_byte = getHashSign(env);
    //toHexString
    jsize array_size=(env)->GetArrayLength(sha1_byte);
    jbyte* sha1 =(env)->GetByteArrayElements(sha1_byte,NULL);
    char hex_sha[array_size*2+1];
    int i;
    for (i = 0;i<array_size;++i) {
        hex_sha[2*i]=HexCode[((unsigned char)sha1[i])/16];
        hex_sha[2*i+1]=HexCode[((unsigned char)sha1[i])%16];
    }
    hex_sha[array_size*2]='\0';
    return strcmp(hex_sha, sign) == 0;
}

extern "C"
JNIEXPORT jstring JNICALL Java_com_lynpo_jni_JniVisitor_stringFromJNI(JNIEnv *env, jclass) {
    std::string hello = "Hello from C++, my name is ";
    std::string name = Lynpo_Const::LYNPO_NAME;
    std::string sentence = hello.append(name);

    if (hashSignValid(env, app_signature_sha1)) {
        return env->NewStringUTF(sentence.c_str());
    } else {
        return env->NewStringUTF("A u kidding me $_$");
    }
}

extern "C"
JNIEXPORT jstring JNICALL Java_com_lynpo_jni_JniVisitor_hashSignFromJNI(JNIEnv *env, jclass) {
    jbyteArray sha1_byte = getHashSign(env);
    //toHexString
    jsize array_size=(env)->GetArrayLength(sha1_byte);
    jbyte* sha1 =(env)->GetByteArrayElements(sha1_byte,NULL);
    char hex_sha[array_size*2+1];
    int i;
    for (i = 0;i<array_size;++i) {
        hex_sha[2*i]=HexCode[((unsigned char)sha1[i])/16];
        hex_sha[2*i+1]=HexCode[((unsigned char)sha1[i])%16];
    }
    hex_sha[array_size*2]='\0';
    return env->NewStringUTF(hex_sha);
}
