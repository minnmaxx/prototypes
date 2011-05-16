/*
 * PrintHello.cpp
 *
 *  Created on: Mar 9, 2011
 *      Author: azhao
 */

#include "PrintHello.h"

#include <iostream>

PrintHello::PrintHello() {


}

PrintHello::~PrintHello() {

}

void PrintHello::print() {
	std::cout << "hello from static 2" << std::endl;
}
