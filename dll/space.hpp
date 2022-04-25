#pragma once
#include <cstdlib>
#include <cstring>

template <class T>
class Space {
   public:
    Space(int size);
    Space(int size, int step);
    T* get();
    T* get_bystep();
    T *head;
    int get_size();
   private:
    int size;
    T *pos;
    int leave;
};

template <class T>
Space<T>::Space(int size) {
    this->pos = (T*) malloc(size * sizeof(T));
    memset(this->pos,0,size * sizeof(T));
    this->size = size;
    this->leave = size;
    this->head = pos;
    this->pos--;
}

template <class T>
T* Space<T>::get() {
    if (this->leave == 0) {
        printf("No memory\n");
    }
    this->pos++;
    this->leave--;
    return pos;
}

template <class T>
int Space<T>::get_size() {
    return size * sizeof(T);
}