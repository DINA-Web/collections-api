package se.nrm.dina.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.nrm.dina.datamodel.Agent;
import se.nrm.dina.datamodel.Attachment;
import se.nrm.dina.datamodel.Collectionobject;

@Generated(value="EclipseLink-2.7.0.v20160118-rNA", date="2016-01-19T06:20:34")
@StaticMetamodel(Collectionobjectattachment.class)
public class Collectionobjectattachment_ extends BaseEntity_ {

    public static volatile SingularAttribute<Collectionobjectattachment, Agent> modifiedByAgentID;
    public static volatile SingularAttribute<Collectionobjectattachment, Agent> createdByAgentID;
    public static volatile SingularAttribute<Collectionobjectattachment, Integer> collectionMemberID;
    public static volatile SingularAttribute<Collectionobjectattachment, Collectionobject> collectionObjectID;
    public static volatile SingularAttribute<Collectionobjectattachment, Attachment> attachmentID;
    public static volatile SingularAttribute<Collectionobjectattachment, Integer> collectionObjectAttachmentID;
    public static volatile SingularAttribute<Collectionobjectattachment, String> remarks;
    public static volatile SingularAttribute<Collectionobjectattachment, Integer> ordinal;

}