//============================================================================
// Name        : helloworld.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <vector>
#include <string>
#include <map>

#include "PrintHello.h"
#include "PrintHi.h"

int main() {

//	PrintHello printHello;
//	printHello.print();

	std::map<std::string,int> items;

	items[ "a" ] = 1;
	items[ "b" ] = 2;

	std::map<std::string,int>::const_iterator iter;
	for( iter = items.begin(); iter != items.end(); ++iter ) {
		std::cout << iter->first << " " << iter->second << std::endl;
	}

//	std::vector<int> items;
//	items.push_back( 1 );
//	items.push_back( 3 );
//
//	std::vector<int>::const_iterator iter;
//	for( iter = items.begin(); iter != items.end(); ++iter ) {
//		std::cout << *iter << std::endl;
//	}

	//std::cout << "!!!Hello World!!!" << std::endl; // prints !!!Hello World!!!
	return 0;
}
