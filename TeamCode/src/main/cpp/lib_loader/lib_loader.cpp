
#include "lib_loader.hpp"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
    sdk::jvm = vm;
    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_teamcode_nativeInit(JNIEnv* env, jobject cur_opmode)
{

}

extern "C" JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_teamcode_nativeLoop(JNIEnv* env, jobject cur_opmode)
{

}






