
int main()
{
  int x;
  int args;

  printf("Enter an integer: ");
  if (( args = scanf("%d", &x)) == 0) {
      printf("Error: not an integer\n");
  } else {
      printf("Read in %d\n", x);
  }
}