package se.nrm.dina.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.nrm.dina.datamodel.Agent;
import se.nrm.dina.datamodel.Attachment;
import se.nrm.dina.datamodel.Dnasequence;

@Generated(value="EclipseLink-2.7.0.v20160118-rNA", date="2016-01-21T08:43:44")
@StaticMetamodel(Dnasequenceattachment.class)
public class Dnasequenceattachment_ extends BaseEntity_ {

    public static volatile SingularAttribute<Dnasequenceattachment, Integer> dnaSequenceAttachmentId;
    public static volatile SingularAttribute<Dnasequenceattachment, Agent> modifiedByAgentID;
    public static volatile SingularAttribute<Dnasequenceattachment, Agent> createdByAgentID;
    public static volatile SingularAttribute<Dnasequenceattachment, Attachment> attachmentID;
    public static volatile SingularAttribute<Dnasequenceattachment, Dnasequence> dnaSequenceID;
    public static volatile SingularAttribute<Dnasequenceattachment, String> remarks;
    public static volatile SingularAttribute<Dnasequenceattachment, Integer> ordinal;

}