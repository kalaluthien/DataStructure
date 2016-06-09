자신의 코드 출력값이 실제 정답 중 하나인지 체크해주는 프로그램입니다.

실행 방법

1. 폴더 안의 .java 파일들을 모두 컴파일한다.
2. 다음 3개의 txt 파일 위치를 argument로 주고 SubwayTester를 실행시킨다.
	- 자신의 코드에 넣은 data
	- 자신의 코드에 넣은 input
	- 자신의 코드가 출력한 output

실행 예제(linux)

cd Tester
javac *.java
java SubwayTester [data] [input] [output]

샘플 파일

data - subway.txt
input - in.txt
output - out_sample.txt || 자기 코드의 output

=> java SubwayTester subway.txt in.txt out_sample.txt