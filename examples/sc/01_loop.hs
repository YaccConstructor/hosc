// supercompilation should terminate

data Number  = Z | S Number;
data Boolean  = True  | False ;

f1 x y d
where

f = \p r h -> 
    case g p  of { Z -> h r; S w -> f w (S r) h; };


f1 = \p r h ->
    case g p  of { Z -> h r; S w -> f w (S r) h; };

g = \x1 -> x1;