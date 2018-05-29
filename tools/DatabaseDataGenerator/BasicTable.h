//
// Created by 星落_月残 on 2018/3/28.
//
#ifndef LIBRARY_SWAP
#define LIBRARY_SWAP
template <class T>
void Swap(T &a, T&b)
{
	T tmp=a;
	a=b;
	b=tmp;
}
#endif //LIBRARY_SWAP

#ifndef BASICDATASTRUCTUREANDALGORITHMS_BASICTABLE_H
#define BASICDATASTRUCTUREANDALGORITHMS_BASICTABLE_H

#include <algorithm>


template<class T>
class Table
{
public:
	Table()=delete;
	Table(int row,int col);
	Table(const Table &another);
	virtual ~Table();
	Table<T> &operator =(const Table &another);
	void Clear();
	int getRow() const;
	int getCol() const;
	T &getData(int row,int col);
	void setData(int row,int col,T input);
protected:
	int rowSize;
	int colSize;
	T **Primacy;
};


template<class T>
Table<T>::Table(int row,int col)
		:rowSize(row),colSize(col)
{
	Primacy=new T *[row];
	for(int i=0;i<row;i++)
		Primacy[i]=new T[col];
}
template<class T>
Table<T>::Table(const Table &another)
		:rowSize(another.rowSize),colSize(another.colSize)
{
	Primacy=new T *[rowSize];
	for(int i=0;i<rowSize;i++)
	{
		Primacy[i]=new T[colSize];
		for(int j=0;i<colSize;j++)
			Primacy[i][j]=another.Primacy[i][j];
	}
}
template<class T>
Table<T>::~Table()
{
	Clear();
}
template<class T>
Table<T> &Table<T>::operator =(const Table &another)
{
	Table<T> tmp(another);
	Swap(this->Primacy,tmp.Primacy);
	Swap(this->colSize,tmp.colSize);
	Swap(this->rowSize,tmp.rowSize);
	return *this;

}
template<class T>
void Table<T>::Clear()
{
	for(int i=0;i<rowSize;i++)
		if(Primacy[i]!=nullptr)
			delete Primacy[i];
	delete[] Primacy;
}
template<class T>
int Table<T>::getRow() const
{
	return rowSize;
}
template<class T>
int Table<T>::getCol() const
{
	return colSize;
}
template<class T>
T &Table<T>::getData(int row,int col)
{
	if(row>=rowSize||col>=colSize)
		return Primacy[rowSize-1][colSize-1];
	return Primacy[row][col];
}
template<class T>
void Table<T>::setData(int row,int col,T input)
{
	if(row>=rowSize||col>=colSize)
		return;
	Primacy[row][col]=std::move(input);
}

#endif //BASICDATASTRUCTUREANDALGORITHMS_BASICTABLE_H
