package dao;

import model.Item;

public interface IItemDao {
    Item add( Item item );
    
    Item setProperty1( Item item );
    Item setProperty2( Item item );
    
    Item find( long id );
}
