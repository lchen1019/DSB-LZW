#include "space.hpp"
#include <cstring>
#include<cstdio>

class Node {
    public:
    int value,key;
};

class a{
public:
// int* p1 = new int;
// int* p2 = new int;
// int* p3 = new int;
// int* p4 = new int;
// int* p5 = new int;
// int* p6 = new int;
// int c;
// int *p = new int[10];
int a[10];
};

int main() {
    Space<Node> space(4096);
    printf("%d\n",space.head);
    for(int i = 0;i < 4096; i++) {
        Node *cur = space.get();
        cur->key = i;
        cur->value = i;
    }
    for(int i=0;i<4096;i++){
        printf("%d   %d\n",space.head[i].key,space.head[i].value);
    }
    Space<Node> space2(4096);
    memcpy(space2.head,space.head,4096 * sizeof(Node));
    memcpy(space2.head,space.head,4096 * sizeof(Node));
    memcpy(space2.head,space.head,4096 * sizeof(Node));
    for(int i=0;i<4096;i++){
        printf("%d   %d\n",space2.head[i].key,space2.head[i].value);
    }
    // a b;
    // printf("%d\n",sizeof(a));
    // printf("%d\n",b.p1);
    // printf("%d\n",b.p2);
    // printf("%d\n",b.p3);
    // printf("%d\n",b.p4);
    // printf("%d\n",b.p5);
    // printf("%d\n",b.p6);
}