//
// Created by Sam on 6/27/2026.
//

#include "../../Header/Hardware/Dc_Motor_Ex.hpp"

Dc_Motor_Ex::Dc_Motor_Ex(jobject motor) noexcept
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

Dc_Motor_Ex::~Dc_Motor_Ex()
{
    JNIEnv* env = getEnv();

    env->DeleteGlobalRef(dcMotorSimple);
    env->DeleteGlobalRef(jclazz);
}

void Dc_Motor_Ex::setPower(jdouble power)
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



