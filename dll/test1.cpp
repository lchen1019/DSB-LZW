#include<cstdio>
#include "space.hpp"

class A{
public:
    int a;
    int b[5];
};

int main() {
    A c;
    printf("%d\n",sizeof(A));
    printf("%d\n",&c.a);
    printf("%d\n",c.b);
    printf("%d\n",&c.b[1]);
    printf("%d\n",&c.b[2]);
}