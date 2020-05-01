import java.io.*;
import java.util.Scanner;
import java.util.*;
import java.text.*;

class IncomeTaxCalculator {
public static void main(String []args) throws IOException{

MainMenu menu = new MainMenu();
menu.displayMenu();
}
}

class MainMenu {
//creating the main menu
void displayMenu() throws IOException{

BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
int ch = 1;
do{
System.out.println("--------------------------------------------------------------------------------");
System.out.println("\t\t\t\t Income Tax Calculator");
System.out.println("\t\t\t\t\t Main Menu");
System.out.println(" 1. Add New Employee Record ");
System.out.println(" 2. Display All Employee Details ");
System.out.println(" 3. Search Employee By PAN & Display Income Tax Details ");
System.out.println(" 4. Exit ");
System.out.println("--------------------------------------------------------------------------------");
System.out.print(" Enter Your Choice: ");
ch= Integer.parseInt(br.readLine());

switch(ch){
case 1:
EmployeeAdd empAdd = new EmployeeAdd();
empAdd.store();
break;
case 2:
EmployeeDisplay empDisplay = new EmployeeDisplay();
empDisplay.display();
break;
case 3:
ParticularDisplay pDisplay = new ParticularDisplay();
pDisplay.displayPart();
break;
case 4: System.exit(0);

default: System.out.println(" Invalid Choice!!!!!!!");

}
}while(ch != 4);
br.close();
}
}

class EmployeeAdd{
//Creating file to store employee details
void store() throws IOException{
FileOutputStream fos = new FileOutputStream("employees.dat", true);

DataOutputStream dos = new DataOutputStream(fos);

String nm, pan, address;
double sincome, ptd, C80, D80, E80, G80;
int age;
char gender, ch;

do{
Scanner sn = new Scanner(System.in);

System.out.print("Enter Employee Name:");
nm = sn.nextLine();

System.out.print("Enter Employee Address:");
address = sn.nextLine();

System.out.print("Enter Employee 10 digit PAN:");//alpha numeric
pan = sn.nextLine();

System.out.print("Enter Employee age:");
age = sn.nextInt();

System.out.print("Enter Employee Gender (M/F):");
gender = sn.next().charAt(0);

System.out.print("Enter Employee Salary Income:");
sincome = sn.nextDouble();

System.out.print("Enter Employee Profession Tax Deducted:");//2400
ptd = sn.nextDouble();

System.out.print("Enter Employee 80C Contributions - less than 150k:");
C80=sn.nextDouble();

System.out.print("Enter Employee 80D Medical Insurance - Self & Parents - less than 35k:");
D80=sn.nextDouble();

System.out.print("Enter Employee 80E Interest Payment on Education Loan:");
E80=sn.nextDouble();

System.out.print("Enter Employee 80G Donation:");
G80=sn.nextDouble();

//Write values to a file
//nm, pan, address
dos.writeUTF(nm);
dos.writeUTF(pan);
dos.writeUTF(address);

//age, gender
dos.writeInt(age);
dos.writeChar(gender);

//sincome, ptd, C80, D80, E80, G80;
dos.writeDouble(sincome);
dos.writeDouble(ptd);
dos.writeDouble(C80);
dos.writeDouble(D80);
dos.writeDouble(E80);
dos.writeDouble(G80);

System.out.print("Do you want to add any more Employees? (Y/N): ");
ch = (char)sn.next().charAt(0);
}while(ch == 'Y');

dos.close();
}
}

class EmployeeDisplay{
String nm, pan, address;
double income, tax=0, sincome, ptd, C80, D80, E80, G80, gti, cVI, eduCess, surCharge = 0, taxP;
int age;
char gender, ch;
boolean EOF = false;

void display() throws IOException{
//Read employee data
FileInputStream fis = new FileInputStream("employees.dat");
DataInputStream dis = new DataInputStream(fis);

System.out.println(" NAME\t\tPAN\t\tAGE\tGENDER\tINCOME\t\tP TAX\t80C\t\t80D\t80E\t80G");
System.out.println("============================================================================="+
"=================================");
while(!EOF){
try{
nm = dis.readUTF();
pan = dis.readUTF();
address = dis.readUTF();

//age, gender
age = dis.readInt();
gender = dis.readChar();

//sincome, ptd, C80, D80, E80, G80;
sincome = dis.readDouble();
ptd = dis.readDouble();

C80 = dis.readDouble();
D80 = dis.readDouble();
E80 = dis.readDouble();
G80 = dis.readDouble();
System.out.println(nm + "\t" + pan + "\t" + age + "\t"  + gender + "\t"  + sincome + "\t"
+ ptd + "\t"  + C80 + "\t"  + D80 + "\t"  + E80 + "\t"  + G80);
}catch(IOException ex){
EOF = true;
}
}
dis.close();
}
}


class ParticularDisplay{

String nm, pan, address, inputPan;
double income, tax=0, sincome, ptd, C80, D80, E80, G80, gti, cVI, eduCess, surCharge = 0, taxP;
int age, chk = 0;
char gender, ch;
boolean EOF = false;

public void displayPart() throws IOException{
//Read employee data
FileInputStream fis = new FileInputStream("employees.dat");
DataInputStream dis = new DataInputStream(fis);
BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

System.out.print("Enter Employee's PAN number: ");
inputPan = br1.readLine();

while(!EOF){
try{
nm = dis.readUTF();
pan = dis.readUTF();
address = dis.readUTF();

//age, gender
age = dis.readInt();
gender = dis.readChar();

//sincome, ptd, C80, D80, E80, G80;
sincome = dis.readDouble();
ptd = dis.readDouble();
if(ptd > 2400){
ptd = 2400;
}

C80 = dis.readDouble();
if(C80 > 150000){
C80 = 150000;
}

D80 = dis.readDouble();
if(D80 > 35000){
D80 = 35000;
}
E80 = dis.readDouble();
G80 = dis.readDouble();

if(pan.equalsIgnoreCase(inputPan)){
chk = 1;
income=sincome-ptd-C80-D80-E80-G80;
System.out.println();
if(age <=60){//all fixed rates
if(income<=250000)
tax=0;
if(income>=250001 && income<=500000)
tax=(income - 250000)*0.1;
if(income>=500001 && income<=1000000)
tax=((income-500000)*0.2)+ 25000;
if(income >= 1000001)
tax=((income-1000000)*0.3)+ 125000;
}else if (age>60 && age<=80){
if(income<=300000)
tax=0;
if(income>=300001 && income<=500000)
tax=(income - 250000)*0.1;
if(income>=500001 && income<=1000000)
tax=((income-500000)*0.2)+ 20000;
if(income >= 1000001)
tax=((income-1000000)*0.3)+ 120000;
}else if(age>80){
if(income<=500000)
tax=0;
if(income>=500001 && income<=1000000)
tax=((income-500000)*0.2);
if(income >= 1000001)
tax=((income-1000000)*0.3)+ 100000;
}
if(income > 10000000){//Taxable Income > 1 Cr
surCharge = tax * 0.1;
}
eduCess = (tax + surCharge) * 0.03;
taxP = tax + surCharge + eduCess;
gti = sincome-ptd;
cVI = C80+D80+E80+G80;

Date today = new Date();
DateFormat fmt = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.UK);
String dat = fmt.format(today);
System.out.println("\t\t\t\t\t\t\tDate: "+ dat);
System.out.println("======================================================================================");
System.out.println("\t\t\tINCOME TAX FOR THE FINANCIAL YEAR 2014-15");
System.out.println("======================================================================================");
System.out.println("");
System.out.println("Name of the Assesse:\t\t\t"+nm);
System.out.println("Address:\t\t\t\t"+address);
System.out.println("Permanent Account Number - PAN:\t\t"+pan);
System.out.println("Age of Assesse:\t\t\t\t"+age);
System.out.println("Gender of Assesse:\t\t\t"+gender);
System.out.println();
System.out.println("Income from Salary:\t\t\t\t\t"+sincome);
System.out.println("Profession Tax Deducted:\t\t\t\t"+ptd);
System.out.println("Gross Total Income (GTI):\t\t\t\t"+gti);
System.out.println();
System.out.println("Deductions under Chapter VI:-");
System.out.println("u/s 80C Contributions:\t\t\t"+C80);
System.out.println("u/s 80D Medical Insurance:\t\t"+D80);
System.out.println("u/s 80E Interest on Education Loan:\t"+E80);
System.out.println("u/s 80G Donations:\t\t\t"+G80);
System.out.println("Total Deductions under Chapter VI:\t\t\t"+cVI);
System.out.println();
System.out.println("Taxable Income (GTI-Chapter VI):\t\t\t"+income);
System.out.println();
System.out.println("Income Tax (IT):\t\t\t\t\t"+tax);
System.out.println("Surcharge (SC):\t\t\t\t\t\t"+surCharge);
System.out.println("Education Cess (EC):\t\t\t\t\t"+eduCess);
System.out.println("Tax Payable (IT + SC + EC):\t\t\t\t"+taxP);
System.out.println();
System.out.println("PLEASE PAY THE TAX BEFORE THE DUE DATE");
System.out.println();
}
}catch(IOException ex){
EOF = true;
}
}
if(chk==0){
System.out.println("Sorry!!Employee details not found in the database");
}
dis.close();
}
}
