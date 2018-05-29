cmake_minimum_required(VERSION 3.5)
project(DatabaseDataGenerator)

set(CMAKE_CXX_STANDARD 17)

add_executable(DatabaseDataGenerator main.cpp SpecializedForPropertyManagement.cpp SpecializedForPropertyManagement.h NameData.h BasicTable.h MultipurposeSequence.h)