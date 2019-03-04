package wl.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class ResBody<T extends Object> implements Serializable{
        /**
         * serialVersionUID
         */
        private String retCode;
        private String retDesc;
        private T rspBody;
}
