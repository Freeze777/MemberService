package com.service.member.utils;

import com.google.common.base.Strings;
import com.service.member.model.entity.Image;
import com.service.member.model.entity.Member;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class MemberServiceUtils {

    public static Image getImageFromMultipartFile(@NonNull final MultipartFile file) throws IOException, SQLException {
        byte[] bytes = file.getBytes();
        Blob blob = new SerialBlob(bytes);
        return Image.builder().blob(blob).build();
    }

    public static void merge(Member existingMember, Member member) {

        if (!Strings.isNullOrEmpty(member.getDateOfBirth())) {
            existingMember.setDateOfBirth(member.getDateOfBirth());
        }
        if (!Strings.isNullOrEmpty(member.getFirstName())) {
            existingMember.setFirstName(member.getFirstName());
        }
        if (!Strings.isNullOrEmpty(member.getLastName())) {
            existingMember.setLastName(member.getLastName());
        }
        if (!Strings.isNullOrEmpty(member.getPostalCode())) {
            existingMember.setPostalCode(member.getPostalCode());
        }
        if (member.getImage() != null) {
            existingMember.setImage(member.getImage());
        }
    }
}
