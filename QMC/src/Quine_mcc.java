import java.io.*;
import java.util.Scanner;
public class Quine_mcc
{
	
    public static void start_initialise_both(int a[][],int value)
    {
        int i,j;
        for(i=0; i<a.length; i++)
        for(j=0; j<a[i].length; j++)
        a[i][j]=value;
    }
    
    public static void start_initialise_single(int a[],int value)
    {
        int i;
        for(i=0; i<a.length; i++)
        a[i]=value;
    }
    
    public static void main(String args[])throws IOException
    {
      
        int n_minterms,n_variables;
        int i,j,k;
        int a[][], b[][], pi[][], checker[];
        int pos=0, controller=0,controller2=0, counter=0;
        int temp1,temp2,check1,check2,check3;;
        
        boolean check = false;
        
        System.out.println("Number of variables: ");
        Scanner input = new Scanner(System.in);
        n_variables = input.nextInt();
        
        System.out.println("Number of minterms: ");
        n_minterms = input.nextInt();
        
        System.out.println("Enter the minterms (with space): ");
        int [] mint = new int[n_minterms];
        for(int p = 0; p < n_minterms; p++)
        {
           mint[p] = input.nextInt();
        }
 
        //***************************************************************************************************
            
        a = new int[ n_minterms * ( n_minterms + 1 ) / 2 ][ n_variables ];
        b = new int[ n_minterms * ( n_minterms + 1 ) / 2 ][ n_variables ];
        
        pi = new int[ n_minterms * ( n_minterms + 1 ) / 2 ][ n_variables ];
        
        checker = new int [ n_minterms * ( n_minterms + 1 ) / 2 ];
        
        start_initialise_both(a,-1);
        for(i=0; i<n_minterms; i++)
        for(j=0; j<n_variables; j++)
        a[i][j]=0;
        //stores binary forms of each minterm elements in matrix a[][]
        for(i=0; i<n_minterms; i++){
            temp1 = mint[i];
            pos = n_variables-1;
            while(temp1>0){
                a[i][pos]=temp1%2;
                pos--;
                temp1/=2;
            }
        }
       
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("MINTERMS (in binary form): ");
        for(i=0;i<n_minterms;i++){
            for(j=0;j<n_variables;j++)
            System.out.print(a[i][j]);
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------");       
        while(true){
            counter=0;
            controller=0;
            start_initialise_both(b,-1);  //for every pass creates new empty matrix b 
            start_initialise_single(checker,-1);
            
            for(i=0; i<a.length; i++){
                if(a[i][0]==-1)
                break;
                for(j=i+1; j<a.length; j++){
                	check1=0;
                    if(a[j][0]==-1)
                    break;
                    for(k=n_variables-1; k>=0; 
                    		k--)
                    if(a[i][k]!=a[j][k]){
                        pos=k;
                        check1++;
                        }
                    
                    if(check1==1){
                        counter++;
                        checker[i]++;
                        checker[j]++;
                        
                        for(k=n_variables-1;k>=0;k--)
                        b[controller][k]=a[i][k];
                        b[controller][pos]=9;
                        controller++;
                    }
                }
            }

            for(j=0; j<i; j++){
                if(checker[j]==-1){
                    for(k=0;k<n_variables;k++)
                    pi[controller2][k]=a[j][k];
                    check3=0;
                    
                    //check for repetition 
                    //if it is found code will ignore it
                   
                    for(temp1=controller2-1; temp1>=0; temp1--){
                    	check2=0;
                        for(temp2=0; temp2<n_variables; temp2++){
                            if(pi[temp1][temp2] != pi[controller2][temp2])
                            	check2++;}
                        if(check2==0){
                        	check3++;
                            break;
                            }
                    }
                    if(check3==0)
                    controller2++;
                }
            }
            
            if(counter==0)   //it will stop if there is not term 
            break;
                 
            for(i=0; i<b.length; i++)
                for(j=0; j<b[i].length; j++)
                a[i][j]=b[i][j];
            
        }
        
        
        System.out.println("THE PRIME IMPLICANTS: ");
        for( i=0; i<controller2; i++){
            for( j=0; j<n_variables; j++){
                if( pi[i][j] == 9 )
                	continue;
                else if( pi[i][j] == 0 )
                	System.out.print((char)('A'+j)+"\'");
                else if( pi[i][j] == 1 )
                    System.out.print((char)('A'+j));
            }
            System.out.println();
        }
        }
        
    
    public static boolean match(int min,int a[][],int row,int n_variables)
    {
        int b[]=new int[n_variables], i=n_variables-1, c=0;
        start_initialise_single(b,0);
        while( min>0 ){
            b[i] = min%2;
            min/=2;
            i--;
        }
        for(i=0; i<n_variables; i++){
            if( a[row][i] == 9 )
            continue;
            if( a[row][i] != b[i] )
            c++;
        }
        if(c==0)
        return true;
        return false;
    }
}