package se.nrm.dina.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.nrm.dina.datamodel.Sgrbatchmatchresultset;

@Generated(value="EclipseLink-2.6.0.v20130922-rNA", date="2016-03-16T12:32:56")
@StaticMetamodel(Sgrbatchmatchresultitem.class)
public class Sgrbatchmatchresultitem_ { 

    public static volatile SingularAttribute<Sgrbatchmatchresultitem, Sgrbatchmatchresultset> batchMatchResultSetId;
    public static volatile SingularAttribute<Sgrbatchmatchresultitem, Long> id;
    public static volatile SingularAttribute<Sgrbatchmatchresultitem, Float> maxScore;
    public static volatile SingularAttribute<Sgrbatchmatchresultitem, Integer> qTime;
    public static volatile SingularAttribute<Sgrbatchmatchresultitem, String> matchedId;

}