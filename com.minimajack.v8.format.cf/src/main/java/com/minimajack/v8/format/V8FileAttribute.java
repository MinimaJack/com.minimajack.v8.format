package com.minimajack.v8.format;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;

import com.minimajack.v8.utils.DateUtils;

public class V8FileAttribute extends BlockHeader {

	public static final int ATTIBUTES_SIZE = 24; // 2x8 + 1x4 + 1x4

	public static final int MINIMUM_PAYLOAD_SIZE = 32; // 2x8 + 1x4 + 1x4

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-16LE");

	private long creationDate;

	private long modifyDate;

	private String name;

	private int attributes;

	public V8FileAttribute() {
		super();
	}

	public V8FileAttribute(ByteBuffer buffer, int position) {
		super(buffer, position);
	}

	@Override
	public void read() throws IOException {
		this.readHeader();
		this.creationDate = getBuffer().getLong();
		this.modifyDate = getBuffer().getLong();
		getBuffer().getInt(); // reserved

		byte[] stringArray = new byte[this.getDocSize() - ATTIBUTES_SIZE];
		this.getBuffer().get(stringArray);
		V8FileAttribute.this.name = new String(stringArray, DEFAULT_CHARSET);
		this.attributes = getBuffer().getInt();
	}

	@Override
	public void write(DataOutput buffer) throws IOException {
		int payloadSize = getPayloadSize();

		this.setDocSize(payloadSize);

		buffer.writeLong(creationDate);
		buffer.writeLong(modifyDate);
		buffer.writeInt(0);
		buffer.write(name.getBytes(DEFAULT_CHARSET));
		buffer.writeInt(attributes);
		buffer.write(new byte[this.getBlockSize() - payloadSize]);
	}

	@Override
	public void setDocSize(int docSize) {
		super.setDocSize(Math.max(MINIMUM_PAYLOAD_SIZE, docSize));
	}

	public int getPayloadSize() {
		int payloadSize = ATTIBUTES_SIZE;
		byte[] names = name.getBytes(DEFAULT_CHARSET);
		payloadSize += names.length;
		return payloadSize;
	}

	public Date getCreationDate() {
		return DateUtils.fromv8Time(this.creationDate);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = DateUtils.tov8Time(creationDate);
	}

	public Date getModifyDate() {
		return DateUtils.fromv8Time(this.modifyDate);
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = DateUtils.tov8Time(modifyDate);
	}

	public int getAttributes() {
		return attributes;
	}

	public void setAttributes(int attributes) {
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
