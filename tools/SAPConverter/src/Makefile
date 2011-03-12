CC = gcc

CFLAGS = -O2 -Wall
LDFLAGS = -s
DEFS = -DHAVE_CONFIG_H -DASAP -DSOUND
LIBS = -lm 
ASAP2WAV = asap2wav

ATARI800_SRC_DIR = .
COMMON_C = asap.c ${ATARI800_SRC_DIR}/cpu.c ${ATARI800_SRC_DIR}/mzpokeysnd.c ${ATARI800_SRC_DIR}/pokeysnd.c ${ATARI800_SRC_DIR}/remez.c
COMMON_H = asap.h asap_internal.h config.h players.h ${ATARI800_SRC_DIR}/cpu.h ${ATARI800_SRC_DIR}/mzpokeysnd.h ${ATARI800_SRC_DIR}/pokey.h ${ATARI800_SRC_DIR}/pokeysnd.h ${ATARI800_SRC_DIR}/remez.h

all: ${ASAP2WAV}

${ASAP2WAV}: asap2wav.c ${COMMON_C} ${COMMON_H}
	${CC} ${CFLAGS} ${LDFLAGS} -o $@ ${DEFS} -I. -I${ATARI800_SRC_DIR} asap2wav.c ${COMMON_C} ${LIBS}

clean:
	rm -f ${ASAP2WAV} 
