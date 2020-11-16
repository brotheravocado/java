/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newclass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class NewClass {
    int order;
    int semester;
    String professeorname;
    int Max;
    int Min;
    boolean checkname = false;
    String total;
    String []a;
    String Grade;
    String realGrade[] = new String[2];
    int count = 0;
    String perfect;
    NewClass(String total,int o,int s,String grade,String n,int max, int min){
        this.total = total;
        order = o;
        a = total.split("/");
        semester = s;
        System.out.println(semester);
        Grade = grade;
        professeorname = n;
        Max = max;
        Min = min;
        perfect = a[0]+"/"+a[1]+"/"+a[2]+"/"+a[3]+"/" +"1/"+ Grade+"/" + professeorname +"/"+ Max +"/"+ Min;         //강좌번호/강좌(강의)명/학과/학점/강좌생성여부(1)/학년/교수명/최소/최대/신청인원
         check(); // 강의 개설
    }
    public void check(){ // 강의개설

        boolean yes = false;
        boolean b = false;
        boolean number = false;
            try { 
            String s;
            String []arr;
            
            File classFile = new File("DB\\Lecture\\AllClass.txt");
            FileReader fileReader = new FileReader(classFile);
            BufferedReader AllClassreader = new BufferedReader(fileReader);
            boolean end = false; // 한번 체크가 되면 이후 줄 끝까지는 확인이 안된다.
            boolean onetwo = false;
            while ((s = AllClassreader.readLine()) != null) { 
                if(!end){
                    arr = s.split("/");
                if(arr[0].equals(a[0])){ // 선택한 강좌 번호가 개설 되었는지 확인
                   if(namecheck()){ // 교수가 재직중인가
                       if(semester==0){ // 1학기
                           onecheck(); // 1학기가 개설되었는지 체크
                           end = true;
                       }
                       else if (semester==1){ // 2학기
                           twocheck();// 2학기가 개설되었는지 체크
                           end = true;
                       }
                       else{
                          JOptionPane.showMessageDialog(null, "잘못된 입력입니다."); 
                          end = true;
                       }
                   }
                   else{ // 교수가 재직중이지않음 
                       end = true;
                        JOptionPane.showMessageDialog(null, "재직중인 교수가 아닙니다");                       
                   }
                }
 
                }
 	   }  if(!end){ // 강좌가 개설되어있지않으면 
                    if(namecheck()){ // 교수가 재직중인가
                       if(semester==0){ // 1학기
                           onetwo = true;
                           NewCreate(onetwo); // 1학기 개설

                       }
                       else if (semester==1){ // 2학기
                           onetwo = false;
                          NewCreate(onetwo); // 2학기 개설

                       }
                       else{
                          JOptionPane.showMessageDialog(null, "잘못된 입력입니다."); 

                       }                        
                        
                    }
                    else{ // 교수가 재직중이지 않으면 
                     JOptionPane.showMessageDialog(null, "재직중인 교수가 아닙니다");                      
                    }
           }

 	   AllClassreader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public boolean namecheck(){ // 교수가 재직중인지 확인한다.
                    boolean yesno = false;
         try { 
            String s;
            String []arr;

            File File = new File("DB\\information.txt");
            FileReader namefileReader = new FileReader(File);
            BufferedReader namereader = new BufferedReader(namefileReader);
            
            while ((s = namereader.readLine()) != null) {
                arr = s.split("/");
                if(professeorname.equals(arr[2])&&arr[0].charAt(0) == 'P'){
                    yesno = true;
                }
 	   } 
 	   namereader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return yesno;
    }
    public void onecheck(){ // 강좌번호 txt에 1학기 강의가 개설되어있는지 확인
        String fileName ="DB\\Lecture\\"+a[0]+".txt" ;
        
        try{
            String number[]; // txt파일의 첫줄 강의번호
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            number= br.readLine().split("/");
            if(number[0].equals(a[0])) //1학기가 개설됨
            {
                JOptionPane.showMessageDialog(null, "동일한 강의가 이번 학기에 개설되었습니다.");
            }
            else{
                onecreate();
            }
        }catch (FileNotFoundException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void twocheck(){ // 강좌번호 txt에 2학기 강의가 개설되어있는지 확인
               String fileName ="DB\\Lecture\\"+a[0]+".txt" ;
                    String number[] = null; 
        try{
            String s;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            number= br.readLine().split("/");
            boolean yesno = false;
            int count = 0;
            while ((s = br.readLine()) != null) { // txt파일 모두 읽기
                            if(!yesno){
                                if(s.equals("-----------------------------------------------------------------") ){ // 만약 2학기 구분자가 있다면 
                                     number = br.readLine().split("/");
                                     yesno = true; // 2학기가 개설되었는지 체크
                            } 
                            }
                            count++;
                          }
            if(count == 0){
                twocreate();
            }
            else{
           if(number[0].equals(a[0])){ // 2학기 강의가 있음
            JOptionPane.showMessageDialog(null, "동일한 강의가 이번 학기에 개설되었습니다.");
        }
        else{
            twocreate();
        }
            }
          br.close();

        }catch (FileNotFoundException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void NewCreate(boolean onetwo ){ // 새로운 강의를 개설한다.
                        try{         
            String fileName ="DB\\Lecture\\"+a[0]+".txt" ;
            // 파일 객체 생성
            File file = new File(fileName) ;
            // true 지정시 파일의 기존 내용에 이어서 작성
            FileWriter fw = new FileWriter(file,true) ;
             
            // 파일안에 문자열 쓰기
            if(!onetwo){ // 2학기로 체크되었다면
              fw.write("-----------------------------------------------------------------\r\n");   
            }
            fw.write(perfect); // 새 강의를 추가 저장
                
            fw.flush();
            // 객체 닫기
            fw.close(); 
        }catch(Exception e){
            e.printStackTrace();
        }
        classinfoSetting();
        AllClassSetting();
    }
    public void onecreate(){ // 2학기가 개설된 1학기 강의정보 입력

		try {
                  String fileName ="DB\\Lecture\\"+a[0]+".txt" ;	
		String read = "";
			BufferedReader br = new BufferedReader(new FileReader(fileName));
                          String s;
                          String renew[] = total.split("/");
                          renew[4] = "1";
                          read += renew[0]+"/"+renew[1]+"/"+renew[2]+"/"+renew[3]+"/"+renew[4]+ "\r\n"; // 첫줄에 정보 입력
                          
                        while ((s = br.readLine()) != null) { // txt파일 모두 읽기
			    read += (s + "\r\n" ); 
                          }
			// FileWriter를 이용해서 덮어쓰기
			FileWriter fw = new FileWriter(fileName);
			fw.write(read);
			fw.close();
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                
                    classinfoSetting();
                    AllClassSetting();
    }
    public void twocreate(){ // 1학기가 개설된 2학기 강의정보 입력
	

		try {
                            String fileName ="DB\\Lecture\\"+a[0]+".txt" ;
                            	BufferedWriter bos = new BufferedWriter(new FileWriter(fileName,true));
                          	String read = "";
                            read+= "\r\n-----------------------------------------------------------------\r\n"; // 학기 구분자
                            read += perfect; // 마지막 줄에 정보 입력
			// FileWriter를 이용해서 덮어쓰기
			bos.write(read);
			bos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

                    classinfoSetting();
                    AllClassSetting();
    }
public void classinfoSetting(){ // 강의가 개설됨을 알려주기위해 classinfo에 강의존재여부(1)변경
    		try {
                    
			BufferedReader br = new BufferedReader(new FileReader("DB\\\\classinfo1.txt"));
                        	String read = "";
                          String s;

                        while ((s = br.readLine()) != null) { // txt파일 모두 읽기
                            if(total.equals(s)){
                          String renew[] = total.split("/");
                          renew[4] = "1";
                          read += renew[0]+"/"+renew[1]+"/"+renew[2]+"/"+renew[3]+"/"+renew[4]+"\r\n"; // 첫줄에 정보 입력
                            }
                            else
			    read += (s + "\r\n" ); 
                          }
			// FileWriter를 이용해서 덮어쓰기
			FileWriter fw = new FileWriter("DB\\\\classinfo1.txt");
			fw.write(read);
			fw.close();
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
}  
public void AllClassSetting(){ // 생성된 강의를 강의목록에 추가한다.
             try{
	BufferedWriter bos = new BufferedWriter(new FileWriter("DB\\Lecture\\AllClass.txt",true));
	bos.write("\r\n"+perfect);
	bos.close();
	JOptionPane.showMessageDialog(null, "개설되었습니다");
        }catch (Exception ex){
              JOptionPane.showMessageDialog(null, "개설 실패");
        }  
}
    public static void main(String []args){
       
    }
    }


