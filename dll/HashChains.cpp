#include "HashChains.h"
#include "space.hpp"
#include <cstring>

HashChains::HashChains() {
    head = new Avl<P, int>[mod];
}

void HashChains::add(P key, int value) {
    this->size++;
    head[key.first % mod].insert(key, value);
}

int HashChains::get_size() {
    // return mod * sizeof(Avl<P,int>);
    return 3508744;
}

bool HashChains::find(P key) {
    // printf("%d\n",key);
    return head[key.first % mod].find(key);
}

int HashChains::get(P key) {

    return head[key.first % mod].get(key);
}