#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <algorithm>
#include <vector>
#include <cmath>
#include <climits>

using namespace std;

char *sym = "BIHMQR";
char *name[] = {"Bubble","Insertion","Heap","Merge","Quick","Radix"};

void test_sort(int type, int num){
	printf("%9s sort test (%4d elements) => ",name[type],num);

	srand(time(NULL));
	vector<int> buf;
	FILE *out = fopen("in","w");
	fprintf(out,"%d\n",num);
	for(int i=0;i<num;i++){
		buf.push_back(rand()-RAND_MAX/2);
		fprintf(out,"%d\n",buf[i]);
	}
	fprintf(out,"%c\nX\n",sym[type]);
	fclose(out);

	system("java SortingTest < in > out");

	sort(buf.begin(),buf.end());
	FILE *in = fopen("out","r");
	int i;
	for(i=0;i<num;i++){
		int tmp;
		fscanf(in,"%d",&tmp);
		if(buf[i]!=tmp) break;
	}
	fclose(in);
	if(i<num) printf("Bad\n");
	else printf("Good\n");
}

void test_time(int type,int num,int start,int end,int iter,FILE *res){
	printf("%9s sort time (%7d elements, %10d~%10d, %3d iter)",name[type],num,start,end,iter);

	FILE *out = fopen("in","w");
	fprintf(out,"r %d %d %d\n%c\nX\n",num,start,end,sym[type]);
	fclose(out);

	vector<int> t;
	for(int l=0;l<iter;l++){
		printf(".");
		system("java SortingTest < in > out");

		FILE *in = fopen("out","r");
		int tmp;
		fscanf(in,"%dms",&tmp);
		t.push_back(tmp);
		fclose(in);
	}

	int mymax=INT_MIN;
	int mymin=INT_MAX;
	int mymed;
	double myavg=0;
	double my2thmoment=0;
	double myvar;
	double mystd;
	for(int i=0;i<iter;i++){
		mymax = mymax>t[i] ? mymax:t[i];
		mymin = mymin<t[i] ? mymin:t[i];
		myavg += t[i];
		my2thmoment += t[i]*t[i];
	}
	myavg /= iter;
	my2thmoment /= iter;
	myvar = my2thmoment - myavg*myavg;
	mystd = sqrt(myvar);
	sort(t.begin(),t.end());
	mymed = t[iter/2];

	printf("done! (max:%d,min:%d,med:%d,avg:%.2lf,std:%.2lf)\n",mymax,mymin,mymed,myavg,mystd);

	fprintf(res,"%s,%d,%d,%d,%d,",name[type],num,start,end,iter);
	for(int i=0;i<iter;i++){
		fprintf(res,"%d,",t[i]);
	}
	fprintf(res,"%d,%d,%d,%lf,%lf\n",mymax,mymin,mymed,myavg,mystd);
}

// 1. 같은 폴더에 SortingTest.class 넣는다.
// 2. java 환경변수가 설정되있어야 한다. PATH에 어쩌고저쩌고/bin 있어야 되고, CLASSPATH에 . 있어야 된다. 네이버에 검색하면 나옴
// 3. 실행 ㄱㄱ
int main(int argc, char **argv){
	// 0~5가 각각 소트 타입임

	// test_sort(x,y)하면 소트 x를 원소 y개 넣고 돌려서 잘 되는지 확인
	for(int i=1;i<=1000;i*=10){
		for(int j=0;j<6;j++){
			test_sort(j,i);
		}
	}
	int arr[] = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};

	// test_time(x,y,start,end,iter,file)하면 소트 x를 start~end 범위의 랜덤 원소 y개 넣고 iter번 돌려서
	// 소트종류 / 원소갯수 / start / end / 돌린횟수 / 데이터 ... 데이터 / 최대값 / 최소값 / 중간값 / 평균값 / 표준편차
	// 형태로 file에다가 액셀로 뽑아줌
	FILE *res = fopen("result.csv","w");
	for(int i = 0 ; i < 7; i++){
		for(int j = 0; j < 6; j++){
			if(arr[i]>=100000 && j<2) continue; // 10만 이상은 버블이랑 인설트 안돌림, 알아서 고치시죠
			for(int k = 1; k <= 1000000; k*=10)
				test_time(j, arr[i], (-1)*k, k, 5, res);
		}
	}
	fclose(res);

	system("PAUSE");
	return 0;
}