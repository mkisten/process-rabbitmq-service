package ru.ertelecom.rabbitmqservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propId;

    private Object NA;
    private Object cp;
    private String Id;
    @ElementCollection
    private List<String> dev;
    private String grp;
    private int sla;
    private String src;
    private String area;
    private String view;
    private String asset;
    private String dbuid;
    private String layer;
    private String cntArg;
    private String cntMku;
    private String cntOlt;
    private String cntOut;
    private String cntSrv;
    private String device;
    private String isList;
    private String region;
    private String repeat;
    private String source;
    private String uptime;
    private String address;
    private String cntEbox;
    private String cntHbox;
    private String endTime;
    private String eventId;
    private String incType;
    private String mapName;
    private String segment;
    private String armUhsId;
    private String deviceId;
    private String floorNum;
    private String isActive;
    private String latitude;
    private String portName;
    private String software;
    private String Resp_dept;
    private String armAreaId;
    private String armCityId;
    private String cntIndAgr;
    private String cntLegAgr;
    private String eventName;
    private String incObject;
    private String incReason;
    private String incSource;
    private String influence;
    private String longitude;
    private String porcheNum;
    private String startTime;
    private String timestamp;
    private String armAddress;
    private String att_object;
    private String childCount;
    private String deviceType;
    private String mainReason;
    private String netAddress;
    private String numEntPort;
    private String armCampusId;
    private String armSectorId;
    private String branch_name;
    private String class_event;
    private String deviceClass;
    private int cntIndAgrNow;
    private int cntLegAgrNow;
    private String templateName;
    private String accidentLogId;
    private String incReasonGuts;
    private String priorityLevel;
    private String hierarchyLevel;
    private String rootDeviceName;
    private String unifiedHouseId;
    private String deviceJournalId;
    private String portDescription;
    private String unifiedStreetId;
    private String parentHierarchyLevel;
}
