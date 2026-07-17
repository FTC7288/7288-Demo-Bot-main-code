//
// Created by Sam on 6/27/2026.
//

#include "../../Header/Hardware/dc_motor_ex.hpp"

dc_motor_ex::dc_motor_ex(jobject motor) noexcept
{
    JNIEnv* env = getEnv();

    dcMotorSimple = env->NewGlobalRef(motor);

    jclass localClass = env->GetObjectClass(motor);
    jclazz = (jclass)env->NewGlobalRef(localClass);
    env->DeleteLocalRef(localClass);

    setPowerMethod =
            env->GetMethodID(
                    jclazz,
                    "setPower",
                    "(D)V"
            );

    if (env->ExceptionCheck())
    {
        env->ExceptionDescribe();
        env->ExceptionClear();
    }
}

dc_motor_ex::~dc_motor_ex()
{
    JNIEnv* env = getEnv();

    env->DeleteGlobalRef(dcMotorSimple);
    env->DeleteGlobalRef(jclazz);
}

void dc_motor_ex::setPower(jdouble power)
{
    JNIEnv* env = getEnv();

    env->CallVoidMethod(
            dcMotorSimple,
            setPowerMethod,
            power
    );

    if (env->ExceptionCheck())
    {
        env->ExceptionDescribe();
        env->ExceptionClear();
    }
}



