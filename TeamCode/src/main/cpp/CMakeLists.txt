cmake_minimum_required(VERSION 4.1.2)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

project("sdk_loader")

file(GLOB_RECURSE ALL_SOURCES "*.c" "*.cpp")
file(GLOB_RECURSE ALL_HEADERS "*.h" "*.hpp")

add_library(lib_loader SHARED
        ${ALL_SOURCES}
        ${ALL_HEADERS}
)

target_include_directories(lib_loader PRIVATE
        ${CMAKE_CURRENT_SOURCE_DIR}
        ${CMAKE_CURRENT_SOURCE_DIR}/Header
        ${CMAKE_CURRENT_SOURCE_DIR}/lib_loader
)

find_library(log-lib log)

target_link_libraries(lib_loader ${log-lib})