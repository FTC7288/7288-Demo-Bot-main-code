#pragma once

#include "../Header/Util.hpp"

JNIEnv* getEnv()
{
    JNIEnv* env = nullptr;
    sdk::jvm->AttachCurrentThread(
            reinterpret_cast<JNIEnv**>(&env),
            nullptr
    );
    return env;
}

namespace sdk
{
    JavaVM* jvm = nullptr;
}