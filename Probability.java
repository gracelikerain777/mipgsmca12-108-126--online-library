import java.io.*;
class p1
{
      public static double w1(double N,double n,double R,double r)  //----------->This function Calculates and returns W1 value.
      {

          return Math.log10(((r+0.5)/(R+1))/((n+1)/(N+2)));

      }


     public static double w2(double N,double n,double R,double r)  //------------>This function Calculates and returns W2 value.
     {
 
        return Math.log10(((r+0.5)/(R+1))/((n-r+0.5)/(N-R+1)));

     }


    public static double w3(double N,double n,double R,double r)  //------------->This function Calculates and returns W3 value.
    {
 

         return Math.log10(((r+0.5)/(R-r+0.5))/((n+1)/(N-n+1)));

    }

    public static double w4(double N,double n,double R,double r)  //-------------->This function Calculates and returns W4 value.
    {

        return Math.log10(((r+0.5)/(R-r+0.5))/((n-r+0.5)/(N-n-(R-r)+0.5)));


    }


 public static int Next_Max_Index(double a[][],int k,int N)    //--------------------->This function Calculates and returns the next maximam value's index in an array.
 {

     int i=0,j=0;
     double m=0;

    for(i=0;i<N;i++)
    if(a[0][i]!=1.0)
    {

      m=a[1][i];
      break;

    }

   for(i=0;i<N;i++)
   if(k==0)
   {

       if(a[1][i]>=m)
       {
          m=a[1][i];
          j=i;
       }

   }
  else
  {
      if(a[1][i]>=m&&a[0][i]!=1.0)
      {
   
          m=a[1][i];
           j=i;

      }
  }

 return j;

}

   public static int ntf(String s,String s1[])   //---------------------------------------->This function Calculates and returns no of times term occured in total documents.
   {


  for(int i=0;i<s1.length;i++)
  if(s.equalsIgnoreCase(s1[i]))
    return 1;
  return 0;

  }

   public static int rtf(String s,String s1[])  //--------------------------------------->This function Calculates and returns no of times term occured in Relavance documents.
   {

 for(int i=0;i<s1.length;i++)
  if(s.equalsIgnoreCase(s1[i])&&s1[s1.length-1].equals("1"))
    return 1;
  return 0;

  }

  public static void print(String s1)  //---------------------------------------------->This function  Makes easy printing while writting code.
  {

    System.out.print("\n"+s1);

  }


public static void main(String s2[])throws Exception //------------------->This is main function, which acts as Driver function whose respnosibility is co-ordinating and excuting all other functions and reading input from the keyboard,files and displaying the output on screen.
{

   print("\nIMPORTANT\n\nMUST READ FOLLOWING INSTRUCTIONS BEFORE GOING TO ENTER INPUT:\n\n1. This programme is not case sensitive.\n\n2. If the doucument is relevant enter 1 otherwise 0 in it's Relevant status.\n\n3. There should be only one white space between the words in the documents.\n\n4. While entering path if you use backslash charecter(\\) it must prefixed with another backslash charecter. Something like this--->\\\\.\n\n5. This Programme does not open sub directories in the directory.\n\n6. This Programme reads only first line from every document.\n\n7. THIS PROGRAMME CONSTRUCTED & WRITTEN BY SATHISH.M");

   int i,j=0,N,R=0,n[],r[],k;
   BufferedReader b,fp1;
   b=new BufferedReader(new InputStreamReader(System.in));
   String query[],documents[],s[],s1[];
   File fp,fp2;
fr f=new fr();

   print("\nEnter the Query:");
   query=(b.readLine()).split(" ");
   n=new int[query.length];
   r=new int[query.length];
   double w[][],D[][],v[][];
   w=new double[4][query.length];

  print("Enter the path of directory which cantains documents starting from root:");
  fp=new File(b.readLine());

  if(fp.exists())
  {

    s1=fp.list();
    N=s1.length;
    v=new double[query.length][N];
    D=new double[4][N];
   documents=new String[N];
 
   print("\nTotally "+N+" documents / sub directories found in that directory. This programme reads first line from each document, and you enter it's relevant status.");

   for(i=0;i<N;i++)
   {

     fp1=new BufferedReader(new FileReader(fp+"\\"+s1[i]));
     fp2=new File(fp+"\\"+s1[i]);
  
      if(fp2.isFile())
      {
//String fs=(fp2+"");
         documents[j]=f.readFile(fp2);
         print("\nEnter the Relevance status of document (-->"+documents[j]+"<--) "+(j+1)+" (1/0):");
         documents[j]=documents[j]+" "+b.readLine();
         s=documents[j].split(" ");
         if(s[s.length-1].equals("1"))
           R++;
         j++;

    }

  }

  for(i=0;i<n.length;i++)
  {

  n[i]=0;
  r[i]=0;

    for(j=0;j<documents.length;j++)
    {

      n[i]+=ntf(query[i],documents[j].split(" "));
      r[i]+=rtf(query[i],documents[j].split(" "));

    }
 
  }

  print("TABLE-1\n");

  for(i=0;i<n.length;i++)
  print("\n\n"+query[i]+"----> N="+N+" n="+n[i]+" R="+R+" r="+r[i]);

  print("\nTABLE-2");

  for(i=0;i<query.length;++i)
  {

    w[0][i]=w1(N,n[i],R,r[i]);
    w[1][i]=w2(N,n[i],R,r[i]);
    w[2][i]=w3(N,n[i],R,r[i]);
    w[3][i]=w4(N,n[i],R,r[i]);

    print("\n\n"+query[i]+"----> w1="+w[0][i]+"  w2="+w[1][i]+"  w3="+w[2][i]+"  w4="+w[3][i]);

  }

  for(i=0;i<n.length;i++)
     for(j=0;j<N;j++)
       v[i][j]=(double)ntf(query[i],documents[j].split(" "));

   for(j=0;j<N;j++)
   {

      D[0][j]=(D[1][j]=(D[2][j]=(D[3][j]=0.0)));

      for(i=0;i<query.length;i++)
      {

        D[0][j]+=w[0][i]*v[i][j];
        D[1][j]+=w[1][i]*v[i][j];
        D[2][j]+=w[2][i]*v[i][j];
        D[3][j]+=w[3][i]*v[i][j];

      }

   }

    print("\n\n\nTABLE-3");

    for(j=0;j<N;j++)
    {

    documents[j]=documents[j].replace('1',' ');
    documents[j]=documents[j].replace('0',' ');
    print("\n\n"+documents[j]+"----> w1="+D[0][j]+"  w2="+D[1][j]+"  w3="+D[2][j]+"  w4="+D[3][j]);

    }

  v=new double[2][N];

    for(i=0;i<4;i++)
    {

    print("\n----->Documents in the order of weight W"+(i+1)+"\n");

     for(j=0;j<N;j++)
     {

      v[0][j]=0;
      v[1][j]=D[i][j];

     }

     for(j=0;j<N;j++)
     {

       k=Next_Max_Index(v,j,N);
       v[0][k]=1;
       print("    "+documents[k]);

     }

    }

  }
  else
  print("\nSorry,\nNo Such Directory found in this computer.\nCheck the path once again.\n");

}

}