package com.aummn.suburb.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuburbServiceResponse {
    private Long id;
    private String name;
    private String postcode;
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SuburbServiceResponse other = (SuburbServiceResponse) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (name == null) {
            if (other.name != null)
                return false;
        } else if (postcode == null) {
            if (other.postcode != null)
                return false;
        } else if (!id.equals(other.id) ||
        		!name.equals(other.name) ||
        		!postcode.equals(other.postcode)) {
            return false;
        } else if (id.equals(other.id) &&
        		name.equals(other.name) &&
        		postcode.equals(other.postcode)) {
            return true;
        }
        return false;
    }
}
