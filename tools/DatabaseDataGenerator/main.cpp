#include <iostream>
#include <algorithm>
#include "SpecializedForPropertyManagement.h"


void makeUnique_Test(vector<string> &input)
{
	sort(input.begin(),input.end());
	auto new_end=unique(input.begin(),input.end());
	input.erase(new_end,input.end());
}

int main()
{
	SpecializedForPropertyManagement instance;

	ofstream fout("output.sql");
	auto backup=cout.rdbuf();
	cout.rdbuf(fout.rdbuf());

	instance.department(5);
	printf("%d\n",instance.getDepartment_id().getUsage());
	instance.staff(500);
	printf("%d\n",instance.getStaff_id().getUsage());
	instance.subarea(200);
	printf("%d\n",instance.getSubarea_id().getUsage());
	instance.building(50);
	printf("%d\n",instance.getBuilding_id().getUsage());
	instance.facilities(5000);
	printf("%d\n",instance.getFacilities_id().getUsage());
	instance.overhaulRecord(10000);
	printf("%d\n",instance.getOverhaulRecord_id().getUsage());
	instance.proprietor(5000);
	printf("%d\n",instance.getProprietor_id().getUsage());
	instance.carpark(5000);
	printf("%d\n",instance.getCarpark_id().getUsage());
	instance.ticket(500);
	printf("%d\n",instance.getTicket_id().getUsage());
	instance.dailyTask(5000);
	printf("%d\n",instance.getDailyTask_id().getUsage());
	instance.buildingEntranceRecord(5000);
	printf("%d\n",instance.getBuildingEntranceRecord_id().getUsage());
	instance.chargingItem(5000);
	printf("%d\n",instance.getChargingItem_id().getUsage());
	instance.fee(5000);
	printf("%d\n",instance.getFee_id().getUsage());
	instance.chargingSituation(5000);
	printf("%d\n",instance.getChargingSituation_id().getUsage());
	instance.carIORecord(5000);
	printf("%d\n",instance.getCarIORecord_id().getUsage());

	cout.rdbuf(backup);
	fout.close();


	return 0;
}