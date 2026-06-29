
#include "lib_loader.hpp"
#include <memory>

std::unique_ptr<Dc_Motor_Ex> lift_motor;

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
    sdk::jvm = vm;
    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_teamcode_CppTestOpMode_start(JNIEnv* env, jobject thiz, jobject motor)
{
    lift_motor = std::make_unique<Dc_Motor_Ex>(motor);
}

extern "C" JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_teamcode_CppTestOpMode_run(JNIEnv* env, jobject thiz, jdouble power)
{
    if (lift_motor)
    {
        lift_motor->setPower(power);
    }
}

extern "C" JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_teamcode_CppTestOpMode_dump(JNIEnv* env, jobject thiz)
{

}






