#include "avl.hpp"
typedef long long ll;
typedef pair<ll,short> P;

class HashChains {
   public:
    HashChains();
    void add(P key, int value);
    bool find(P key);
    int get(P key);
    // Avl<P, int> *(*head);
    Avl<P, int> *head;
    int size = 0;
    int get_size();
    
   private:
    const ll mod = 4099;
};
