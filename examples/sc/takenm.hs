data Number = Z | S Number;
data List a = Nil | Cons a (List a);

take n1 m1 where

from = \n -> Cons n (from (S n));

take = \n list ->
    case list of {
      Nil -> Nil;
      Cons x xs -> case n of { Z -> Nil; S y -> Cons x (take y xs);};
    };

tail = \list ->
  case list of {
    Nil-> Nil;
    Cons x xs -> xs;
  };

mapAdd1 = \list ->
  case list of {
    Nil -> Nil;
    Cons x xs -> Cons (S x) (mapAdd1 xs);
  };

takenm = \n m ->  take n (mapAdd1 (from m));