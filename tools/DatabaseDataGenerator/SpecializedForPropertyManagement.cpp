//
// Created by 星落_月残 on 2018/4/16.
//

#include <algorithm>
#include "SpecializedForPropertyManagement.h"
#include "NameData.h"


/*
building 						B+num5
buildingEntranceRecord			BER+num15
carIORecord						CIOR+num15
carpark							C+num5
chargingItem					CI+num5
chargingSituation				CS+num15
dailyTask						DT+num15
department						D+num5
facilities						FC+num15
fee								FE+num10
overhaulRecord					O+num15
proprietor						P+num10
staff							SF+num10
subarea							SA+num5
ticket							T+num10
suggestion						SG+num10
 */

int stringCmp(const string &a,const string &b)
{
	int lengthA=static_cast<int>(a.length());
	int lengthB=static_cast<int>(b.length());
	int shorterOne=(lengthA>lengthB ? lengthB : lengthA);
	for(int i=0;i<shorterOne;i++)
	{
		if(a[i]<b[i])
			return -1;
		else if(a[i]>b[i])
			return 1;
		else if(a[i]==b[i])
			continue;
	}
	if(lengthA<lengthB)
		return -1;
	else
		return 1;
}

SpecializedForPropertyManagement::SpecializedForPropertyManagement()
{
	getRandom.seed(static_cast<unsigned int>(time(nullptr)));
}

string SpecializedForPropertyManagement::getNumberString(int digit)
{
	stringstream ss;
	long long j=1;
	for(int i=0;i<digit;i++)
		j*=10;
	ss<<getRandom()%j;
	return ss.str();
}

string SpecializedForPropertyManagement::getNumberArea(int bottom,int top,int digit)
{
	stringstream ss;
	int gap=top-bottom;
	ss<<(getRandom()%gap+bottom);
	string result=ss.str();
	for(;result.length()<digit;)
		result="0"+result;
	return result;
}
string SpecializedForPropertyManagement::getCharacterString(int digit)
{
	string result;
	for(int i=0;i<digit;i++)
		result+=Alphabeta[getRandom()%Alphabeta.size()];
	return result;
}
string SpecializedForPropertyManagement::getDateString(bool isNew)
{
	string result,year,month,day;
	if(isNew)
		year="20";
	else
		year="19";
	year+=getNumberArea(0,100,2);
	month=getNumberArea(1,13,2);
	int month_num=stoi(month);
	switch(month_num)
	{
		case 2:
			day=getNumberArea(1,29,2);
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day=getNumberArea(1,32,2);
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day=getNumberArea(1,31,2);
			break;
		default:
			day="01";
			break;
	}
	result=year+month+day;
	return result;
}
string SpecializedForPropertyManagement::getTimeString()
{
	string result=getNumberArea(0,24,2)+getNumberArea(0,60,2)+getNumberArea(0,60,2);
	return result;
}

void makeUnique(vector<string> &input)
{
	sort(input.begin(),input.end());
	auto new_end=unique(input.begin(),input.end());
	input.erase(new_end,input.end());
}

void SpecializedForPropertyManagement::department(int number)
{
	for(int i=0;i<number;i++)
	{
		string dept_id="D";
		dept_id+=getNumberString(5);
		department_id.AddFinal(dept_id);
	}
	department_id.MakeUnique(stringCmp);
	for(int i=0;i<department_id.getUsage();i++)
	{
		string dept_id=department_id[i];
		string dept_name=getCharacterString(5);
		cout<<"INSERT INTO department"<<endl<<"VALUES ('"<<dept_id<<"','"<<dept_name<<"');"<<endl;
	}

}

void SpecializedForPropertyManagement::staff(int number)
{
	for(int i=0;i<number;i++)
	{
		string staff_id_loc="SF"+getNumberString(10);
		staff_id.AddFinal(staff_id_loc);
	}
	staff_id.MakeUnique(stringCmp);
	for(int i=0;i<staff_id.getUsage();i++)
	{
		string staff_id_loc=staff_id[i];
		string staff_name=Lastname[getRandom()%Lastname.size()]
						  +FirstName[getRandom()%FirstName.size()]
						  +FirstName[getRandom()%FirstName.size()];
		string gender=(getRandom()%2==0 ? "男" : "女");
		string tel=getNumberString(11);
		string address=RoadName[getRandom()%RoadName.size()]
					   +getNumberString(3)+"弄"+getNumberString(2)+"号"+getNumberString(3)+"室";
		string position=PositionData[getRandom()%PositionData.size()];
		string salary=getNumberString(4);
		string dept=department_id[getRandom()%department_id.getUsage()];
		string elec_qlfy=(getRandom()%2==0 ? "true" : "false");
		string plbr_qlfy=(getRandom()%2==0 ? "true" : "false");
		cout<<"INSERT INTO staff"<<endl<<"VALUES ('"<<staff_id_loc<<"','"<<staff_name
			<<"','"<<gender<<"','"<<tel<<"','"<<address<<"','"<<position<<"',"<<salary
			<<",'"<<dept<<"',"<<elec_qlfy<<","<<plbr_qlfy<<");"<<endl;
	}

}

void SpecializedForPropertyManagement::subarea(int number)
{
	for(int i=0;i<number;i++)
	{
		string subarea_id_loc="SA"+getNumberString(5);
		subarea_id.AddFinal(subarea_id_loc);
	}
	subarea_id.MakeUnique(stringCmp);
	for(int i=0;i<subarea_id.getUsage();i++)
	{
		string subarea_id_loc=subarea_id[i];
		cout<<"INSERT INTO subarea"<<endl<<"VALUES ('"<<subarea_id_loc<<"');"<<endl;
	}

}

void SpecializedForPropertyManagement::building(int number)
{
	for(int i=0;i<number;i++)
	{
		string building_id_loc="B"+getNumberString(5);
		building_id.AddFinal(building_id_loc);
	}
	building_id.MakeUnique(stringCmp);
	for(int i=0;i<building_id.getUsage();i++)
	{
		string building_id_loc=building_id[i];
		string subarea_id_loc=subarea_id[getRandom()%subarea_id.getUsage()];
		string max_floor=getNumberString(2);
		string max_room=getNumberString(2);
		cout<<"INSERT INTO building"<<endl<<"VALUES ('"<<building_id_loc<<"','"<<subarea_id_loc<<"',"
			<<max_floor<<","<<max_room<<");"<<endl;
	}
}

void SpecializedForPropertyManagement::facilities(int number)
{
	for(int i=0;i<number;i++)
	{
		string fclt_id="FC"+getNumberString(15);
		facilities_id.AddFinal(fclt_id);
	}
	facilities_id.MakeUnique(stringCmp);
	for(int i=0;i<facilities_id.getUsage();i++)
	{
		string fclt_id=facilities_id[i];
		string fclt_type=FacilityType[getRandom()%FacilityType.size()];
		string subarea_id_loc=subarea_id[getRandom()%subarea_id.getUsage()];
		string building_id_loc="NULL";
		string floor="NULL";
		if(getRandom()%2==0)
		{
			building_id_loc=building_id[getRandom()%building_id.getUsage()];
			floor=getNumberString(2);
		}
		string location="NULL";
		cout<<"INSERT INTO facilities"<<endl<<"VALUES ('"<<fclt_id<<"','"<<fclt_type<<"','"<<subarea_id_loc<<"',";
		if(building_id_loc!="NULL")
			cout<<"'"<<building_id_loc<<"',"<<floor<<",'"<<location<<"');"<<endl;
		else
			cout<<building_id_loc<<","<<floor<<","<<location<<");"<<endl;
	}
}

void SpecializedForPropertyManagement::overhaulRecord(int number)
{
	for(int i=0;i<number;i++)
	{
		string overhaul_id="O"+getNumberString(15);
		overhaulRecord_id.AddFinal(overhaul_id);
	}
	overhaulRecord_id.MakeUnique(stringCmp);
	for(int i=0;i<overhaulRecord_id.getUsage();i++)
	{
		string overhaul_id=overhaulRecord_id[i];
		string fclt_id=facilities_id[getRandom()%facilities_id.getUsage()];
		string overhaul_type="NULL";
		string overhaul_time=getDateString(true)+getTimeString();
		string handler=staff_id[getRandom()%staff_id.getUsage()];
		string overhaul_result="NULL";
		cout<<"INSERT INTO overhaulRecord"<<endl<<"VALUES ('"<<overhaul_id<<"','"<<fclt_id<<"','"
			<<overhaul_type<<"',"<<overhaul_time<<",'"<<handler<<"','"<<overhaul_result<<"');"<<endl;
	}

}

void SpecializedForPropertyManagement::proprietor(int number)
{
	for(int i=0;i<number;i++)
	{
		string prprt_id="P"+getNumberString(10);
		proprietor_id.AddFinal(prprt_id);
	}
	proprietor_id.MakeUnique(stringCmp);
	for(int i=0;i<proprietor_id.getUsage();i++)
	{
		string prprt_id=proprietor_id[i];
		string prprt_name=Lastname[getRandom()%Lastname.size()]
						  +FirstName[getRandom()%FirstName.size()]
						  +FirstName[getRandom()%FirstName.size()];
		string gender=(getRandom()%2==0 ? "男" : "女");
		string tel=getNumberString(11);
		string birthday=getDateString(false)+getTimeString();
		string aprt_building=building_id[getRandom()%building_id.getUsage()];
		string aprt_floor=getNumberString(3);
		string aprt_room_num=getNumberString(2);
		cout<<"INSERT INTO proprietor"<<endl<<"VALUES ('"<<prprt_id<<"','"<<prprt_name<<"','"<<gender
			<<"','"<<tel<<"',"<<birthday<<",'"<<aprt_building<<"',"<<aprt_floor<<","<<aprt_room_num<<");"<<endl;
	}
}

void SpecializedForPropertyManagement::carpark(int number)
{
	for(int i=0;i<number;i++)
	{
		string carpark_id_loc="C"+getNumberString(5);
		carpark_id.AddFinal(carpark_id_loc);
	}
	carpark_id.MakeUnique(stringCmp);
	for(int i=0;i<carpark_id.getUsage();i++)
	{
		string carpark_id_loc=carpark_id[i];
		string subarea_id_loc=subarea_id[getRandom()%subarea_id.getUsage()];
		string owner_id=proprietor_id[getRandom()%proprietor_id.getUsage()];
		string plate_number="沪"+getCharacterString(1)+getNumberArea(10000,100000,5);
		string valid_term=getDateString(true)+getTimeString();
		cout<<"INSERT INTO carpark"<<endl<<"VALUES ('"<<carpark_id_loc<<"','"<<subarea_id_loc
			<<"','"<<owner_id<<"','"<<plate_number<<"',"<<valid_term<<");"<<endl;
	}
}

void SpecializedForPropertyManagement::ticket(int number)
{
	for(int i=0;i<number;i++)
	{
		string ticket_id_loc="T"+getNumberString(10);
		ticket_id.AddFinal(ticket_id_loc);
	}
	ticket_id.MakeUnique(stringCmp);
	for(int i=0;i<ticket_id.getUsage();i++)
	{
		string ticket_id_loc=ticket_id[i];
		string ticket_type="NULL";
		string ticket_time=getDateString(true)+getTimeString();

		bool flag=(getRandom()%2==0);
		string initiator_prprt_id=proprietor_id[getRandom()%proprietor_id.getUsage()];
		string initiator_staff_id=staff_id[getRandom()%staff_id.getUsage()];
		if(flag)
			initiator_prprt_id="NULL";
		else
			initiator_staff_id="NULL";
		string subarea_id_loc=subarea_id[getRandom()%subarea_id.getUsage()];
		string aprt_building=building_id[getRandom()%building_id.getUsage()];
		string aprt_floor=getNumberString(3);
		string aprt_room_num=getNumberString(2);
		string description="NULL";
		string handler_id=staff_id[getRandom()%staff_id.getUsage()];
		string handle_time=getDateString(true)+getTimeString();
		string ticket_result="NULL";
		string ticket_fdbk=getNumberString(1);
		cout<<"INSERT INTO ticket"<<endl<<"VALUES ('"<<ticket_id_loc<<"','"<<ticket_type<<"',"<<ticket_time;
		if(flag)
			cout<<","<<initiator_prprt_id<<",'"<<initiator_staff_id<<"','";
		else
			cout<<",'"<<initiator_prprt_id<<"',"<<initiator_staff_id<<",'";
		cout<<subarea_id_loc<<"','"<<aprt_building<<"',"<<aprt_floor<<","<<aprt_room_num<<",'"<<description
			<<"','"<<handler_id<<"',"<<handle_time<<",'"<<ticket_result<<"',"<<ticket_fdbk<<");"<<endl;
	}
}

void SpecializedForPropertyManagement::dailyTask(int number)
{
	for(int i=0;i<number;i++)
	{
		string task_id="DT"+getNumberString(15);
		dailyTask_id.AddFinal(task_id);
	}
	dailyTask_id.MakeUnique(stringCmp);
	for(int i=0;i<dailyTask_id.getUsage();i++)
	{
		string task_id=dailyTask_id[i];
		string task_type=TaskType[getRandom()%TaskType.size()];
		string task_time=getDateString(true)+getTimeString();
		string task_area=subarea_id[getRandom()%subarea_id.getUsage()];
		string handler=staff_id[getRandom()%staff_id.getUsage()];
		string task_result="NULL";
		string isException=(getRandom()%2==0 ? "TRUE" : "FALSE");
		cout<<"INSERT INTO dailyTask"<<endl<<"VALUES ('"<<task_id<<"','"<<task_type<<"',"
			<<task_time<<",'"<<task_area<<"','"<<handler<<"','"<<task_result<<"',"<<isException<<");"<<endl;
	}
}

void SpecializedForPropertyManagement::buildingEntranceRecord(int number)
{
	for(int i=0;i<number;i++)
	{
		string record_id="BER"+getNumberString(15);
		buildingEntranceRecord_id.AddFinal(record_id);
	}
	buildingEntranceRecord_id.MakeUnique(stringCmp);
	for(int i=0;i<buildingEntranceRecord_id.getUsage();i++)
	{
		string record_id=buildingEntranceRecord_id[i];
		string prprt_id=proprietor_id[getRandom()%proprietor_id.getUsage()];
		string building_id_loc=building_id[getRandom()%building_id.getUsage()];
		string access_time=getDateString(true)+getTimeString();
		string verify_type=(getRandom()%2==0 ? "key" : "card");
		cout<<"INSERT INTO buildingEntranceRecord"<<endl<<"VALUES ('"<<record_id<<"','"
			<<prprt_id<<"','"<<building_id_loc<<"',"<<access_time<<",'"<<verify_type<<"');"<<endl;
	}
}

void SpecializedForPropertyManagement::chargingItem(int number)
{
	for(int i=0;i<number;i++)
	{
		string item_id="CI"+getNumberString(5);
		chargingItem_id.AddFinal(item_id);
	}
	chargingItem_id.MakeUnique(stringCmp);
	for(int i=0;i<chargingItem_id.getUsage();i++)
	{
		string item_id=chargingItem_id[i];
		string item_title=CharingItemName[getRandom()%CharingItemName.size()];
		cout<<"INSERT INTO chargingItem"<<endl<<"VALUES ('"<<item_id<<"','"<<item_title<<"');"<<endl;
	}
}

void SpecializedForPropertyManagement::fee(int number)
{
	for(int i=0;i<number;i++)
	{
		string fee_id_loc="FE"+getNumberString(10);
		fee_id.AddFinal(fee_id_loc);
	}
	fee_id.MakeUnique(stringCmp);
	for(int i=0;i<fee_id.getUsage();i++)
	{
		string fee_id_loc=fee_id[i];
		string item_id=chargingItem_id[getRandom()%chargingItem_id.getUsage()];
		string start_date=getDateString(true);
		string end_date=getDateString(true);
		string price=getNumberString(3);
		cout<<"INSERT INTO fee"<<endl<<"VALUES ('"<<fee_id_loc<<"','"<<item_id<<"',"<<start_date
			<<","<<end_date<<","<<price<<");"<<endl;
	}
}

void SpecializedForPropertyManagement::chargingSituation(int number)
{
	for(int i=0;i<number;i++)
	{
		string situation_id="CS"+getNumberString(15);
		chargingSituation_id.AddFinal(situation_id);
	}
	chargingSituation_id.MakeUnique(stringCmp);
	for(int i=0;i<chargingSituation_id.getUsage();i++)
	{
		string situation_id=chargingSituation_id[i];
		string fee_id_loc=fee_id[getRandom()%fee_id.getUsage()];
		string prprt_id=proprietor_id[getRandom()%proprietor_id.getUsage()];
		string collector_id=staff_id[getRandom()%staff_id.getUsage()];
		string charge_date=getDateString(true);
		cout<<"INSERT INTO chargingSituation"<<endl<<"VALUES ('"<<situation_id<<"','"<<fee_id_loc
			<<"','"<<prprt_id<<"','"<<collector_id<<"',"<<charge_date<<");"<<endl;
	}
}

void SpecializedForPropertyManagement::carIORecord(int number)
{
	for(int i=0;i<number;i++)
	{
		string io_record_id="CIOR"+getNumberString(15);
		carIORecord_id.AddFinal(io_record_id);
	}
	carIORecord_id.MakeUnique(stringCmp);
	for(int i=0;i<carIORecord_id.getUsage();i++)
	{
		string io_record_id=carIORecord_id[i];
		string plate_number="沪"+getCharacterString(1)+getNumberArea(10000,100000,5);
		string prprt_id=(getRandom()%2==0 ? proprietor_id[getRandom()%proprietor_id.getUsage()] : "NULL");
		string record_in_time=getDateString(true)+getTimeString();
		string record_out_time=getDateString(true)+getTimeString();
		string price=getNumberString(2);

		cout<<"INSERT INTO carIORecord"<<endl<<"VALUES ('"<<io_record_id<<"','"<<plate_number;
		if(prprt_id=="NULL")
			cout<<"',"<<prprt_id<<",";
		else
			cout<<"','"<<prprt_id<<"',";
		cout<<record_in_time<<","<<record_out_time<<","<<price<<");"<<endl;
	}
}

void SpecializedForPropertyManagement::suggestion(int number)
{
	for(int i=0;i<number;i++)
	{
		string suggestion_id_loc="SG"+getNumberString(10);
		suggestion_id.AddFinal(suggestion_id_loc);
	}
	suggestion_id.MakeUnique(stringCmp);
	for(int i=0;i<suggestion_id.getUsage();i++)
	{
		string suggestion_id_loc=suggestion_id[i];
		string prprt_id=(getRandom()%2==0 ? proprietor_id[getRandom()%proprietor_id.getUsage()] : "NULL");
		string suggestion_type=TaskType[getRandom()%TaskType.size()];
		string suggestion_detail="NULL";

		cout<<"INSERT INTO suggestion"<<endl<<"VALUES ('"<<suggestion_id_loc;
		if(prprt_id=="NULL")
			cout<<"',"<<prprt_id<<",'";
		else
			cout<<"','"<<prprt_id<<"','";
		cout<<suggestion_type<<"','"<<suggestion_detail<<"');"<<endl;
	}
}

void SpecializedForPropertyManagement::sysRole()
{
	int i=0;
	for(;i<PositionData.size();i++)
		cout<<"INSERT INTO SysRole"<<endl<<"VALUES ("<<i<<",'"<<PositionData[i]<<"','NULL',TRUE);"<<endl;
	cout<<"INSERT INTO SysRole"<<endl<<"VALUES ("<<i<<",'proprietor','NULL',TRUE);"<<endl;

}

void SpecializedForPropertyManagement::sysPermission()
{
	cout<<"INSERT INTO SysPermission"<<endl<<"VALUES ("<<0<<",'"<<"all:all"<<"','NULL','NULL','"<<"all:all"<<"',"<<-1<<",'"<<-1<<"',TRUE);"<<endl;
	int i=1;//0设为所有的parent
	for(auto &r :tableList)
	{
		string name=r+":all";
		cout<<"INSERT INTO SysPermission"<<endl<<"VALUES ("<<i<<",'"<<name<<"','NULL','NULL','"<<name<<"',"<<0<<",'"<<0<<"',TRUE);"<<endl;
		permissionMapper.insert(std::move(make_pair(name,i)));
		i++;
	}

	for(auto &t:tableList)
	{
		for(auto &r:actionList)
		{
			string name=t+":"+r;
			int parent=permissionMapper[t+":all"];
			cout<<"INSERT INTO SysPermission"<<endl<<"VALUES ("<<i<<",'"<<name<<"','NULL','NULL','"<<name<<"',"<<parent<<",'"<<parent<<"',TRUE);"<<endl;
			permissionMapper.insert(std::move(make_pair(name,i)));
			i++;
		}
	}


}

void SpecializedForPropertyManagement::rolePermission()
{
	vector<string> basicViewList={"building","chargingItem","department","subarea"};
	vector<string> keyRecordViewList={"buildingEntranceRecord","carIORecord","overhaulRecord","proprietor","staff","suggestion"};
	vector<string> supervisorControlList={"dailyTask","ticket"};
	vector<string> staffViewList={"carpark","facilities"};
	vector<string> accountantControlList={"chargingSituation","fee"};
	vector<string> staffInsertList={"dailyTask","ticket"};

	//manager
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<0<<","<<0<<");"<<endl;
	//supervisor
	for(auto &r:supervisorControlList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<1<<","<<permissionMapper[r+":all"]<<");"<<endl;
	for(auto &r:keyRecordViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<1<<","<<permissionMapper[r+":all"]<<");"<<endl;
	for(auto &r:basicViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<1<<","<<permissionMapper[r+":view"]<<");"<<endl;

	//guard
	for(auto &r:staffInsertList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<2<<","<<permissionMapper[r+":insert"]<<");"<<endl;
	for(auto &r:staffViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<2<<","<<permissionMapper[r+":view"]<<");"<<endl;
	for(auto &r:basicViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<2<<","<<permissionMapper[r+":view"]<<");"<<endl;

	//cleaner
	for(auto &r:staffInsertList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<3<<","<<permissionMapper[r+":insert"]<<");"<<endl;
	for(auto &r:staffViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<3<<","<<permissionMapper[r+":view"]<<");"<<endl;
	for(auto &r:basicViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<3<<","<<permissionMapper[r+":view"]<<");"<<endl;

	//repairman
	for(auto &r:staffInsertList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<4<<","<<permissionMapper[r+":insert"]<<");"<<endl;
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<4<<","<<permissionMapper["overhaulRecord:insert"]<<");"<<endl;
	for(auto &r:staffViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<4<<","<<permissionMapper[r+":view"]<<");"<<endl;
	for(auto &r:basicViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<4<<","<<permissionMapper[r+":view"]<<");"<<endl;

	//accountant
	for(auto &r:accountantControlList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<5<<","<<permissionMapper[r+":all"]<<");"<<endl;
	for(auto &r:staffViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<5<<","<<permissionMapper[r+":view"]<<");"<<endl;
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<5<<","<<permissionMapper["proprietor:view"]<<");"<<endl;
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<5<<","<<permissionMapper["carIORecord:view"]<<");"<<endl;
	for(auto &r:basicViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<5<<","<<permissionMapper[r+":view"]<<");"<<endl;

	//receptionist
	for(auto &r:staffViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<6<<","<<permissionMapper[r+":view"]<<");"<<endl;
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<6<<","<<permissionMapper["proprietor:view"]<<");"<<endl;
	for(auto &r:basicViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<6<<","<<permissionMapper[r+":view"]<<");"<<endl;
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<6<<","<<permissionMapper["chargingSituation:update"]<<");"<<endl;
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<6<<","<<permissionMapper["suggestion:insert"]<<");"<<endl;

	//proprietor
	for(auto &r:basicViewList)
		cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<7<<","<<permissionMapper[r+":view"]<<");"<<endl;
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<7<<","<<permissionMapper["ticket:view"]<<");"<<endl;
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<7<<","<<permissionMapper["ticket:insert"]<<");"<<endl;
	cout<<"INSERT INTO RolePermission"<<endl<<"VALUES ("<<7<<","<<permissionMapper["ticket:update"]<<");"<<endl;

}


const default_random_engine &SpecializedForPropertyManagement::getGetRandom() const
{
	return getRandom;
}
const Sequence<string> &SpecializedForPropertyManagement::getSubarea_id() const
{
	return subarea_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getBuilding_id() const
{
	return building_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getBuildingEntranceRecord_id() const
{
	return buildingEntranceRecord_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getCarIORecord_id() const
{
	return carIORecord_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getCarpark_id() const
{
	return carpark_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getChargingItem_id() const
{
	return chargingItem_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getChargingSituation_id() const
{
	return chargingSituation_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getDailyTask_id() const
{
	return dailyTask_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getDepartment_id() const
{
	return department_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getFacilities_id() const
{
	return facilities_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getFee_id() const
{
	return fee_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getOverhaulRecord_id() const
{
	return overhaulRecord_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getProprietor_id() const
{
	return proprietor_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getStaff_id() const
{
	return staff_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getTicket_id() const
{
	return ticket_id;
}
const Sequence<string> &SpecializedForPropertyManagement::getSuggestion_id() const
{
	return suggestion_id;
}
const map<string,int> &SpecializedForPropertyManagement::getPermissionMapper() const
{
	return permissionMapper;
}

