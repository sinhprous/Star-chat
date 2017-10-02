#include<stdio.h>
#include<string.h>

enum state {q0, q1, q2, q3};
int Delta[4][2] = {q1, q0, q1, q2, q1, q3, q1, q0};
enum state q = q0;

int main(){
  while(1){
  char str[100];
  printf("Nhap xau:.. \n");
  fflush(stdin);
  while(scanf("%s",str)!=0){
  int i = 0;
  int c = str[i] - 97;
  int L = strlen(str);
  while (i < L) {
    if (c==0||c==1){
      q = Delta[q][c];
      i++;
      c = str[i] -97;
    }
    else {
      printf("Xau loi ...");
      break;
    }
  }
  if (i==L)
    if (q==q3) printf("\n%s YES\n\n", str);
      else printf("\n%s NO \n\n", str);
  }
}
  return 0;
}
