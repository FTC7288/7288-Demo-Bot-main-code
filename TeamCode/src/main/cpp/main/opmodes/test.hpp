#pragma once

#include "../../sdk/header/main.hpp"

class test : public op_mode
{
    void native_init() override;
    void native_loop() override;
};


