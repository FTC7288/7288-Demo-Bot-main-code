#pragma once

#include "jni.h"
#include "../SDK/Header/Main.hpp"


extern "C" JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_teamcode_CppTestOpMode_start(JNIEnv *env, jobject thiz, jobject motor);
extern "C" JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_teamcode_CppTestOpMode_run(JNIEnv *env, jobject thiz, jdouble power);
extern "C" JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_teamcode_CppTestOpMode_dump(JNIEnv *env, jobject thiz);
