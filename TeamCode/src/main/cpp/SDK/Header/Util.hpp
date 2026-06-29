#pragma once

#include "jni.h"

#define attach_thread   \
    JNIEnv* env;        \
    sdk::jvm->AttachCurrentThread(reinterpret_cast<JNIEnv**>(&env),nullptr);

JNIEnv* getEnv();

namespace sdk
{
    extern JavaVM* jvm;
}


