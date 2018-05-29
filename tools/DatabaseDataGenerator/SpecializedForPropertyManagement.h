//
// Created by 星落_月残 on 2018/4/16.
//

#ifndef DATABASEDATAGENERATOR_SPECIALIZEDFORPROPERTYMANAGEMENT_H
#define DATABASEDATAGENERATOR_SPECIALIZEDFORPROPERTYMANAGEMENT_H

#include <iostream>
#include <vector>
#include <ctime>
#include <random>
#include <fstream>
#include <sstream>
#include <map>
#include "MultipurposeSequence.h"


using namespace std;


class SpecializedForPropertyManagement
{
public:
	SpecializedForPropertyManagement();
	string getNumberString(int digit);
	string getNumberArea(int bottom,int top,int digit);
	string getCharacterString(int digit);
	string getDateString(bool isNew);
	string getTimeString();
	void subarea(int number);
	void chargingItem(int number);
	void department(int number);
	void building(int number);
	void buildingEntranceRecord(int number);
	void carIORecord(int number);
	void carpark(int number);
	void chargingSituation(int number);
	void facilities(int number);
	void overhaulRecord(int number);
	void dailyTask(int number);
	void fee(int number);
	void proprietor(int number);
	void staff(int number);
	void ticket(int number);
	void suggestion(int number);

	//void userInfo(int number);
	void sysRole();
	void sysPermission();
	//void userRole();
	void rolePermission();

	const default_random_engine &getGetRandom() const;
	const Sequence<string> &getSubarea_id() const;
	const Sequence<string> &getBuilding_id() const;
	const Sequence<string> &getBuildingEntranceRecord_id() const;
	const Sequence<string> &getCarIORecord_id() const;
	const Sequence<string> &getCarpark_id() const;
	const Sequence<string> &getChargingItem_id() const;
	const Sequence<string> &getChargingSituation_id() const;
	const Sequence<string> &getDailyTask_id() const;
	const Sequence<string> &getDepartment_id() const;
	const Sequence<string> &getFacilities_id() const;
	const Sequence<string> &getFee_id() const;
	const Sequence<string> &getOverhaulRecord_id() const;
	const Sequence<string> &getProprietor_id() const;
	const Sequence<string> &getStaff_id() const;
	const Sequence<string> &getTicket_id() const;
	const Sequence<string> &getSuggestion_id() const;

	const map<string,int> &getPermissionMapper() const;
private:
	default_random_engine getRandom;
	Sequence<string> subarea_id;
	Sequence<string> building_id;
	Sequence<string> buildingEntranceRecord_id;
	Sequence<string> carIORecord_id;
	Sequence<string> carpark_id;
	Sequence<string> chargingItem_id;
	Sequence<string> chargingSituation_id;
	Sequence<string> dailyTask_id;
	Sequence<string> department_id;
	Sequence<string> facilities_id;
	Sequence<string> fee_id;
	Sequence<string> overhaulRecord_id;
	Sequence<string> proprietor_id;
	Sequence<string> staff_id;
	Sequence<string> ticket_id;
	Sequence<string> suggestion_id;

	map<string,int> permissionMapper;



};

int stringCmp(const string &a,const string &b);

#endif //DATABASEDATAGENERATOR_SPECIALIZEDFORPROPERTYMANAGEMENT_H
