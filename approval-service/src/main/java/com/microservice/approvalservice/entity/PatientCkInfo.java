package com.microservice.approvalservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xu
 * @since 2023-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PatientCkInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String patientUsername;

    private Integer orderId;

    private String adminUsername;

    private String cancelReason;

    private String auditStatus;

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

;


}
