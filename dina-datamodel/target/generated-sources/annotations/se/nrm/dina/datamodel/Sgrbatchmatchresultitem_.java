package se.nrm.dina.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.nrm.dina.datamodel.Sgrbatchmatchresultset;

@Generated(value="EclipseLink-2.7.0.v20160118-rNA", date="2016-01-19T06:20:34")
@StaticMetamodel(Sgrbatchmatchresultitem.class)
public class Sgrbatchmatchresultitem_ { 

    public static volatile SingularAttribute<Sgrbatchmatchresultitem, Sgrbatchmatchresultset> batchMatchResultSetId;
    public static volatile SingularAttribute<Sgrbatchmatchresultitem, Long> id;
    public static volatile SingularAttribute<Sgrbatchmatchresultitem, Float> maxScore;
    public static volatile SingularAttribute<Sgrbatchmatchresultitem, Integer> qTime;
    public static volatile SingularAttribute<Sgrbatchmatchresultitem, String> matchedId;

}